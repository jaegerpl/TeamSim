/**
 * 
 */
package de.haw.teamsim.team;

import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import de.haw.teamsim.agent.Agent;


/**
 * The Team is the framework for agents to know about other team members availability and to broadcast information to active team members.
 * 
 * @author pascal
 *
 */
public class Team extends Observable {
	public enum Status {active, suspended}

	private String name;
	private HashMap<String, Agent> roles; // the roles in which team members are enacting
	private HashMap<String, Agent> members; 	
	
	public Team(String name){
		this.name = name;
		roles = new HashMap<String, Agent>();
		members = new HashMap<String, Agent>();
	}
	
	public void addTeamMember(Agent member){
		members.put(member.getName(), member);
		roles.put(member.getRole(), member);
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}
	
	public Set<String> getRoles(){
		return roles.keySet();
	}
	
	public Set<String> getMembers(){
		return members.keySet();
	}
	
	public Agent getRoleAgent(String role){
		return roles.get(role);
	}
}
