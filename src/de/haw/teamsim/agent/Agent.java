package de.haw.teamsim.agent;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.haw.teamsim.agent.components.Communication;
import de.haw.teamsim.sim.TeamSim;
import de.haw.teamsim.team.Team;

public class Agent implements Steppable {
	
	private String name;
	private Integer ID;
	private Team team;
	private String role;
	private List<String> skills;
	private Model goalModel;
	private String uri;
	private ICommunication communication;

	public static Logger log = Logger.getLogger(Agent.class);
	
	
	public Agent(int id, Team team){
		ID = id;
		this.team = team;
		skills = new LinkedList<String>();
		uri = TeamSim.getURI();
		communication = new Communication(team, this); 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public void setID(Integer iD) {
		ID = iD;
	}
	
	public void step(SimState state) {
		// TODO implement
	}
	
	/**
	 * Initial doings to put agent into team context
	 */
	public void init(){
		goalModel = TeamSim.getModel();
		Resource myself = goalModel.createResource(uri+"#"+name)
				.addProperty(de.haw.teamsim.semweb.vocabulary.Agent.plays, goalModel.createResource(uri+"#"+role))
				.addProperty(RDF.type, de.haw.teamsim.semweb.vocabulary.Agent.resource);
		try{
			goalModel.write(new PrintWriter(System.out));
        
		} catch (Exception e) {
			System.out.println("Failed: " + e);
		}
		
		team.addTeamMember(this);
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

	/**
	 * Returns a value that indicates how good the agent can receive, understand and use the information
	 * in its current situation
	 */
	public float getRetentiveness() {
		// TODO implement
		return 0;
	}
	
	public void receiveInformation(Agent sender, String topic, int priority, Object info){
		communication.receiveInformation(sender, topic, priority, info);
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole(){
		return role;
	}
}
