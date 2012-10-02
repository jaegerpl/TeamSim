package de.haw.teamsim.experiment1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.continuous.Continuous2D;

public class ExpSim extends SimState {

	private static final long serialVersionUID = 1L;
	public Continuous2D world = new Continuous2D(1.0, 100, 100);

	private List<ExpAgent> agents;		 				// the agents of the simulation
	private List<ExpAction> actionList;					// the ordered actions
	private boolean executing = false;					// indicates if an action is being executed
	private int nextAgent = 0;							// the agent who may submit the next action
	private boolean isFinished = false;
	
	private static long starttime;
	private static long endtime;

	public static Logger log = Logger.getLogger(ExpSim.class);

	public ExpSim(long seed) {
		super(seed);
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		
		agents = new LinkedList<ExpAgent>();
		actionList = new LinkedList<ExpAction>();
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
			Stoppable stop = schedule.scheduleRepeating(a,1000);
			a.setStoppanble(stop);
		}
		for(ExpAction a : actionList){
			Stoppable stop = schedule.scheduleRepeating(a,1000);
			a.setStoppanble(stop);
		}
		
		System.out.println("Start Simulation");
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
		for(int i = 0; i < actionList.size()-1; i++){
			ExpAction a = actionList.get(i);
			if(i>0){
				a.setPredecessor(actionList.get(i-1).getID());
			}
			if(i<actionList.size()){
				a.setSuccessor(actionList.get(i+1).getID());
			}
		}
		System.out.println("Actions created.");
		
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
		System.out.println("Action Sequence = "+actionList.toString());
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
	 * Reads a list of actions from disk
	 * @param location
	 */
	private void readActionsFromDisk(String location, ExpAgent a, ExpAgent b, ExpAgent c){
		
		List<ExpAction> actions = new LinkedList<ExpAction>();
		
		//Read Actions
		StringTokenizer strtok;

		try{
			FileInputStream fstream = new FileInputStream(location);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				strtok = new StringTokenizer(strLine);
				System.out.println("Reading Line: "+strLine);
				while(strtok.hasMoreTokens()){
					String token = strtok.nextToken();
					if(token.equals("Prio")){
						//skip the line
					} else {
						while(strtok.hasMoreTokens()){
							int prio = new Integer(strtok.nextToken());
							int duration = new Integer(strtok.nextToken());
							int pred = new Integer(strtok.nextToken());
							int succ = new Integer(strtok.nextToken());
							actions.add(new ExpAction(prio, duration, pred, succ));
						}							
					}
				}
			}
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		Collections.shuffle(actions);

		agents.addAll(Arrays.asList(a,b,c));
		int agentcount = agents.size(); 
		int count = actions.size()/agentcount; //actions per agent
	
		for(ExpAgent agent : agents){
			for(int i = 0; i< count; i++){
				agent.addAction(actions.remove(0));
			}
		}
		
		// add the remaining actions to one agent
		if(!actions.isEmpty()){ 
			agents.get(0).addActions(actions);
		}
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
			if(!actionList.isEmpty()){			
				if(actionList.get(0).equals(action)){
					System.out.println("Agent: "+submitter+" submitted Action: "+action+" - it will be executed!");
					actionList.remove(0);
					action.execute(this);
					executing = true;
					if(actionList.isEmpty()){
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

	/**
	 * ExpSim is notified by an executed action, that it is finished and a new
	 * action can be executed now.
	 */
	public void startNextRound() {
		executing = false;
		if(!isFinished){
			ExpAgent agent = agents.get(getNextAgent());
			System.out.println("Notifying Agent "+agent.name+" to submit his action");
			agent.notifyForNextSubmission();
		}	
	}
	
	private int getNextAgent(){
		nextAgent += 1;
		if(nextAgent == agents.size()){
			nextAgent = 0;
		}
		return nextAgent;
	}

	public static void main(String[] args) {
		doLoop(ExpSim.class, args);
		endtime = System.currentTimeMillis();
		System.out.println("Duration: "+ (endtime-starttime));
		System.exit(0);
	}
}