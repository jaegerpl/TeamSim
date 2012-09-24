package de.haw.teamsim.experiment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;

import com.hp.hpl.jena.rdf.model.Model;

import de.haw.teamsim.agent.Agent;
import de.haw.teamsim.semweb.SemanticGoalWeb;
import de.haw.teamsim.team.Team;

public class ExpSim extends SimState {

	private static final long serialVersionUID = 1L;
	public Continuous2D world = new Continuous2D(1.0, 100, 100);

	private List<ExpAgent> agents;		 // the agents of the simulation

	public static Logger log = Logger.getLogger(ExpSim.class);

	public ExpSim(long seed) {
		super(seed);
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		
		agents = new LinkedList<ExpAgent>();
	}

	public void start() {
		super.start();
		world.clear();

		log.info("Init Agents");
		initAgents("data/AgentDefinition.txt");
		
		for(ExpAgent a : agents){
			schedule.scheduleRepeating(a);
		}
	}
	
	/**
	 * Reads the agents from the init file and adds them to the simulation.
	 * 
	 * @param location
	 */
	private void initAgents(String location){
		
		List<ExpAction> actions = new LinkedList<ExpAction>();
		
		ExpAgent a = new ExpAgent();
		ExpAgent b = new ExpAgent();
		ExpAgent c = new ExpAgent();
		
		a.addAgents(b, c);
		b.addAgents(a, c);
		c.addAgents(a, b);
		
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
							actions.add(new ExpAction(prio, duration, pred));
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
	
	public static void main(String[] args) {
		doLoop(ExpSim.class, args);
		System.exit(0);
	}
}