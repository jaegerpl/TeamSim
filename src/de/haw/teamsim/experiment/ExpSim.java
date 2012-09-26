package de.haw.teamsim.experiment;

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
import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;

public class ExpSim extends SimState {

	private static final long serialVersionUID = 1L;
	public Continuous2D world = new Continuous2D(1.0, 100, 100);

	private List<ExpAgent> agents;		 // the agents of the simulation
	private Map<ExpAgent, ExpAction> executableActions;
	private List<ExpAction> actionList;

	public static Logger log = Logger.getLogger(ExpSim.class);

	public ExpSim(long seed) {
		super(seed);
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		
		agents = new LinkedList<ExpAgent>();
		executableActions = new HashMap<ExpAgent, ExpAction>();
	}

	public void start() {
		super.start();
		world.clear();

		log.info("Init Agents");
//		initAgents("data/AgentDefinition.txt");
		initAgents();
		createActions();
		
		for(ExpAgent a : agents){
			schedule.scheduleRepeating(a);
		}
	}
	
	private void createActions(){
		int actionCount = 40;
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
		for(int i = 0; i <= actionList.size(); i++){
			ExpAction a = actionList.get(i);
			if(i>0){
				a.setPredecessor(actionList.get(i-1).getID());
			}
			if(i<actionList.size()){
				a.setSuccessor(actionList.get(i+1).getID());
			}
		}
		
		// Randomly split the actions over the agents
		Collections.shuffle(actionList);
		Iterator<ExpAction> iter = actionList.iterator();
		int index = 0;
		while(iter.hasNext()){
			if(index == agents.size()-1){
				index = 0;
			}
			ExpAgent agent = agents.get(index);
			agent.addAction(iter.next());
			index++;
		}
	}
	
	/**
	 * Reads the agents from the init file and adds them to the simulation.
	 * 
	 * @param location
	 */
	private void initAgents(){
		
		ExpAgent a = new EgoAgent();
		ExpAgent b = new EgoAgent();
		ExpAgent c = new EgoAgent();
		
		a.addAgents(b, c);
		a.setSimulation(this);
		b.addAgents(a, c);
		b.setSimulation(this);
		c.addAgents(a, b);
		c.setSimulation(this);
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
	 * @return
	 */
	public boolean submitForExecution(ExpAction action, ExpAgent submitter){
		if(!executableActions.containsValue(submitter)){
			executableActions.put(submitter, action);
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		doLoop(ExpSim.class, args);
		System.exit(0);
	}

	/**
	 * ExpSim is notified by an executed action, that it is finished and a new
	 * action can be executed now.
	 */
	public void startNextRound() {
		// randomly chose an action for execution.
		Collections.shuffle((List<?>) executableActions);
		ExpAction action = executableActions.get(0);
		action.execute(this);
		for(int i = 1; i <= executableActions.size(); i++){
			ExpAction ac = executableActions.get(i);
			ExpAgent owner = ac.getOwner();
			owner.notifyActionRejected(ac);
		}
		
		// one action has been chosen, so agents can submit new actions for submission now
		for(ExpAgent a : agents){
			a.notifyForNextSubmission();
		}
	}
}