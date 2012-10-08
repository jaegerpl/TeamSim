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
	private List<ExpAction> orderedActions;					// the sequenzed actions actions
	private boolean executing = false;					// indicates if an action is being executed
	private int nextAgent = 0;							// the agent who may submit the next action
	private boolean isFinished = false;
	
	private static long starttime;
	private static long endtime;
	
	public MessageSystem msgSys;
	
	
	public static String Agent_A;
	public static String Agent_B;
	public static String Agent_C;
	public static String Team;

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
//		initAgents("data/AgentDefinition.txt");
		initAgents();
		createActions();
		
		for(ExpAgent a : agents){
			Stoppable stop = schedule.scheduleRepeating(a);
			a.setStoppanble(stop);
		}
		for(ExpAction a : actionList){
			Stoppable stop = schedule.scheduleRepeating(a);
			a.setStoppanble(stop);
		}
		
		System.out.println("Start Simulation\n");
		agents.get(nextAgent).notifyForNextSubmission();
	}
	
	/**
	 * Create a random order of actions and randomly spread then over all agent
	 */
	private void createActions(){
		int actionCount = 9;
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
		System.out.println("Actions created.");
		System.out.println("Action Sequence = "+actionList.toString()+"\n");
		System.out.println("Action Sequence = "+orderedActions.toString()+"\n");
		
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
		System.out.println("Actions added to agents.");
	}
	
	/**
	 * Creates three agents
	 * 
	 * @param location
	 */
	private void initAgents(){
		
		ExpAgent a = new CollabAgent("A");
		ExpAgent b = new CollabAgent("B");
		ExpAgent c = new CollabAgent("C");
		
		a.addAgents(b, c);
		a.setSimulation(this);
		b.addAgents(a, c);
		b.setSimulation(this);
		c.addAgents(a, b);
		c.setSimulation(this);
		agents.add(a);
		agents.add(b);
		agents.add(c);
		System.out.println("Added agents to simulatiom list");
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
		if(!executing){
			if(!orderedActions.isEmpty()){			
				if(orderedActions.get(0).equals(action)){
					System.out.println("Agent: "+submitter+" submitted Action: "+action+" - it will be executed!");
					orderedActions.remove(0);
					action.execute(this);
					executing = true;
					if(orderedActions.isEmpty()){
						isFinished = true;
					}
					return true;
				} else {
					System.out.println("Agent: "+submitter+" submitted Action: "+action+" - WRONG waiting for Action: "+actionList.get(0));
					startNextRound();
					return false;
				}
			} else {
				System.out.println("DONE");
				this.kill();
			}
		}
		return false;
		
	}

//	/**
//	 * ExpSim is notified by an executed action, that it is finished and a new
//	 * action can be executed now.
//	 */
//	public void startNextRound() {
//		executing = false;	
//	}
//	
//	public boolean isExecuting(){
//		return executing;
//	}

	public static void main(String[] args) {
		doLoop(ExpSim.class, args);
		endtime = System.currentTimeMillis();
		System.out.println("Duration: "+ (endtime-starttime));
		System.exit(0);
	}
}