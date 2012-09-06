package de.haw.teamsim.sim;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

public class TeamSim extends SimState {

	private static final long serialVersionUID = 1L;
	private String rdfLocation = "file:data/TeamSimGoalOntology.owl";
	private static String uri = "http://www.semanticweb.org/ontologies/2012/5/TeamSimGoalOntology.owl";
	public Continuous2D world = new Continuous2D(1.0, 100, 100);
	
	private Team team;
	private int agentCount = 1;		 // the agent ID 
	private List<Agent> agents;		 // the agents of the simulation

	public static Logger log = Logger.getLogger(TeamSim.class);
	private SemanticGoalWeb goalWeb;
	private static Model model;

	public TeamSim(long seed) {
		super(seed);
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		
		agents = new LinkedList<Agent>();
		
		team = new Team("CrisisTeam");
	}

	public void start() {
		super.start();
		world.clear();

		log.info("Init Agents");
		initAgents("data/AgentDefinition.txt");
		
		log.info("Reading ontology file");
		goalWeb = new SemanticGoalWeb(uri);
		goalWeb.readRDFData(rdfLocation);
		model = goalWeb.getModel();
		
		for(Agent a : agents){
			schedule.scheduleRepeating(a);
			a.init();
		}
	}
	
	/**
	 * Reads the agents from the init file and adds them to the simulation.
	 * 
	 * @param location
	 */
	private void initAgents(String location){
		StringTokenizer strtok;
		
		Agent agent = null;

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
					if(token.equals("Agent")){
						if(agent != null){
							System.out.println(agent);
						}
						agent = new Agent(agentCount++, team);
						agents.add(agent);
					} else if(token.equals("Name:")){
						agent.setName(strtok.nextToken());
					} else if(token.equals("Role:")){
						agent.setRole(strtok.nextToken());
					} else if(token.equals("Team:")){
						// TODO reimplement logic for TEAMS
					} else if(token.equals("Skill:")){
						while(strtok.hasMoreTokens()){
							String skillstr = strtok.nextToken();
							String cutted = (String) skillstr.subSequence(0, skillstr.length()-1);
							System.out.println("The cutted skill is "+cutted);
							agent.addSkill(cutted);
						}						
					} else if(token.equals("#")){	}
				}
			}
			in.close();
			if(agent != null){
				System.out.println(agent);
			}
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static Model getModel(){
		if(model != null){
			return model;
		} else throw new UnsupportedOperationException("Model not initialized.");
	}
	
	public static String getURI(){
		return uri;
	}

	public static void main(String[] args) {
		doLoop(TeamSim.class, args);
		System.exit(0);
	}
}