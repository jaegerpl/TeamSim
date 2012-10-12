package de.haw.teamsim.experiment2.sim;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.continuous.Continuous2D;
import de.haw.teamsim.experiment2.ExpAction;
import de.haw.teamsim.experiment2.ExpAgent;
import de.haw.teamsim.experiment2.agent.CollabAgent;

public class ExpSim extends SimState {

	private static final long serialVersionUID = 1L;
	public Continuous2D world = new Continuous2D(1.0, 100, 100);

	private List<ExpAgent> agents;		 				// the agents of the simulation
	private List<ExpAction> actionList;					// the created actions actions
	private List<ExpAction> orderedActions;				// the sequenced actions actions
	private int nextAgent = 0;							// the agent who may submit the next action
	private ExpAction submittedAction = null;			// the action an agents submits for executing
	
	private static long starttime;
	private static long endtime;
	
	public MessageSystem msgSys;
	
	public static String Agent_A = "A";
	public static String Agent_B = "B";
	public static String Agent_C = "C";
	public static String Team = "TEAM";

	public static Logger log = Logger.getLogger(ExpSim.class);

	public ExpSim(long seed) {
		super(seed);
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		
		agents = new LinkedList<ExpAgent>();
		actionList = new LinkedList<ExpAction>();
		orderedActions = new LinkedList<ExpAction>();
		msgSys = new MessageSystem();
	}

	public void start() {
		super.start();
		starttime = System.currentTimeMillis();
		world.clear();

		log.info("Init Agents");
		initAgents();
		createActions();
		msgSys.addAgents(agents);
		Thread t = new Thread(msgSys);
		t.start();
		
		for(ExpAgent a : agents){
			Stoppable stop = schedule.scheduleRepeating(a);
			a.setStoppanble(stop);
		}
		for(ExpAction a : actionList){
			Stoppable stop = schedule.scheduleRepeating(a);
			a.setStoppanble(stop);
		}
		
		log.info("Start Simulation\n");
		for(ExpAgent a : agents){
			a.setInitialized();
		}
		agents.get(nextAgent).notifyForNextSubmission();
	}
	
	/**
	 * Create a random order of actions and randomly spread then over all agent
	 */
	private void createActions(){
		int actionCount = 6;
		int prio = 1;
		
		// create actions
		for(int i = 1; i <= actionCount; i++){
			if(prio == 5){
				prio = 1;
			}
			actionList.add(new ExpAction(prio, 10, 0, 0));
			prio++;
		}
		
		// create random ordering of actions.
		Collections.shuffle(actionList);
		actionList.get(0).setSuccessorID(actionList.get(1).getID());
		for(int i = 1; i < actionList.size(); i++){
			ExpAction a = actionList.get(i);
			a.setPredecessorID(actionList.get(i-1).getID());
			if(i<actionList.size()-1){
				a.setSuccessorID(actionList.get(i+1).getID());
			}
		}
		orderedActions = new LinkedList<ExpAction>(actionList);
		log.debug("Action Sequence = "+orderedActions.toString()+"\n");
		
		// Randomly split the actions over the agents
		Collections.shuffle(actionList);
		Iterator<ExpAction> iter = actionList.iterator();
		int index = 0;
		while(iter.hasNext()){
			if(index == agents.size()){
				index = 0;
			}
			ExpAgent agent = agents.get(index);
			agent.addAction(iter.next());
			index++;
		}
		log.debug("Actions added to agents.");
	}
	
	/**
	 * Creates three agents
	 * 
	 * @param location
	 */
	private void initAgents(){
		
		ExpAgent a = new CollabAgent(Agent_A);
		ExpAgent b = new CollabAgent(Agent_B);
		ExpAgent c = new CollabAgent(Agent_C);
		
		a.addAgents(b, c);
		a.setSimulation(this);
		b.addAgents(a, c);
		b.setSimulation(this);
		c.addAgents(a, b);
		c.setSimulation(this);
		agents.add(a);
		agents.add(b);
		agents.add(c);
		log.debug("Added agents to simulatiom list");
	}
		
	/**
	 * Returns true, if the action has been accept for execution.
	 * Returns false, if agent already has an action submitted for execution.
	 * 
	 * @param action
	 * @param submitter
	 * @return returns true, if the right sequenced action has been submitted<br> false otherwise
	 */
	public boolean submitForExecution(ExpAction action, ExpAgent submitter){
		if(submittedAction != null){
			log.info("Agent: "+submitter+" submitted Action: "+action+" - WRONG Action "+submittedAction+" is in execution");
			return false;
		} else {
			if(orderedActions.get(0).equals(action)){
				submittedAction = action;
				submittedAction.execute(this);
				log.info("Agent: "+submitter+" submitted Action: "+action+" - it will be executed!");
				return true;
			}
		}
		log.info("Agent: "+submitter+" submitted Action: "+action+" - WRONG waiting for Action: "+orderedActions.get(0));
		return false;
	}
	
	public void markAsExecuted(ExpAction action){
		if(action.equals(submittedAction)){
			orderedActions.remove(0);
			submittedAction = null;
			if(orderedActions.isEmpty()){
				for(ExpAgent agent : agents){
					agent.stopStepping();
				}
				System.out.println("ENDE=ENDE=ENDE=ENDE");
			}
		}
	}

	public static void main(String[] args) {
		doLoop(ExpSim.class, args);
		endtime = System.currentTimeMillis();
		log.info("Duration: "+ (endtime-starttime));
		System.exit(0);
	}
	
	public List<ExpAction> getOrderedAction(){
		return orderedActions;
	}
}