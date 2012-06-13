package de.haw.teamsim.agent;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import de.haw.teamsim.sim.Team;
import de.haw.teamsim.sim.Team.Status;
import de.haw.teamsim.sim.TeamSim;
import de.haw.teamsim.sim.TeamStatusUpdate;

public class Agent implements Steppable, Observer {
	
	public enum AgentRole {Manager, FireDepartement, PolicyDepartement}
	
	private String name;
	private Integer ID;
	private Team team;
	private Status myTeamStatus;
	private List<Agent> communicationPartners;
	private String role;
	private List<String> skills;
	private Model goalModel;
	private String uri;
	
	public void setRole(String role) {
		this.role = role;
	}

	public static Logger log = Logger.getLogger(Agent.class);
	
	
	public Agent(int id){
		ID = id;
		skills = new LinkedList<String>();
		communicationPartners = new LinkedList<Agent>();
		uri = TeamSim.getURI();
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void step(SimState state) {

	}
	
	/**
	 * Initial doings to put agent into team context
	 */
	public void init(){
		goalModel = TeamSim.getModel();
		Resource myself = goalModel.createResource(uri+"#"+name)
				.addProperty(de.haw.teamsim.semweb.vocabulary.Agent.plays, goalModel.createResource(uri+"#"+role));
		try{
			goalModel.write(new PrintWriter(System.out));
        
		} catch (Exception e) {
			System.out.println("Failed: " + e);
		}
		
		System.out.println("AGENT INIT STUFF");
		log.debug("Agent "+name+" im Team registieren");
		team.addObserver(this);
		log.debug("Agent "+name+" lädt Teammitglieder-Liste");
		communicationPartners.addAll(team.getTeamMembers());
		log.debug(name+" kennt jetzt "+communicationPartners.toString());
		team.sendInfo2Team(new Belief(name, "Hi, ich bin "+name));
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
		StringBuffer skill = new StringBuffer();
		for(String str : skills){
			skill.append(str+", ");
		}
		return "Agent(ID: "+ID+", Name: "+name+", Role: "+role+", Team: "+team+", Skills: "+skill+")";
	}
	
	public void addSkill(String skill) {
		skills.add(skill);	
	}
}
