package de.haw.teamsim.agent;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import sim.engine.SimState;
import sim.engine.Steppable;
import de.haw.teamsim.semweb.Node;
import de.haw.teamsim.semweb.SemanticGoalWeb;
import de.haw.teamsim.semweb.SemanticGoalWeb.Predicate;
import de.haw.teamsim.sim.Team;
import de.haw.teamsim.sim.Team.Status;
import de.haw.teamsim.sim.TeamStatusUpdate;

public class Agent implements Steppable, Observer {
	
	public enum AgentRole {Manager, FireDepartement, PolicyDepartement}
	
	String name;
	Integer ID;
	Team team;
	Status myTeamStatus;
	List<Agent> communicationPartners;
	SemanticGoalWeb goalWeb = Node.theWeb;
	
	public static Logger log = Logger.getLogger(Agent.class);
	
	public Agent(String name, AgentRole r, Team t){
		
		log.debug("Starting Agent "+name);
		this.name = name;
		communicationPartners = new LinkedList<Agent>();
		team = t;
		ID = new Random().nextInt();
	}
	
	public void step(SimState state) {

	}
	
	/**
	 * Initial doings to put agent into team context
	 */
	public void init(){
		log.debug("Agent "+name+" im Team registieren");
		team.addObserver(this);
		log.debug("Agent "+name+" lädt Teammitglieder-Liste");
		communicationPartners.addAll(team.getTeamMembers());
		log.debug(name+" kennt jetzt "+communicationPartners.toString());
		team.sendInfo2Team(new Belief(name, "Hi, ich bin "+name));
		Node myNode = new Node(this, ID);
		goalWeb.add(myNode);
		goalWeb.getObjects(Predicate.responsibleRole);
	}

	@Override
	public void update(Observable o, Object arg) {
		log.debug(name+" erhält update information: "+arg+" von "+o);
		TeamStatusUpdate tsu;
		if(arg instanceof TeamStatusUpdate){
			tsu = (TeamStatusUpdate)arg;
			if(tsu.status == Status.suspended){
				communicationPartners.remove(tsu.agent);
				log.debug(name+" weiß, dass "+tsu.agent+" das Team verlassen hat.");
			} else if(tsu.status == Status.active){
				communicationPartners.add(tsu.agent);
				log.debug(name+" kennt jetzt auch "+tsu.agent+".");
			}
		} else if(arg instanceof Belief){
			Belief b;
			b = (Belief)arg;
			log.debug(name+" weiß jetzt von "+o+", dass "+b);
		}
	}
	
	public String toString(){
		return name;
	}
}
