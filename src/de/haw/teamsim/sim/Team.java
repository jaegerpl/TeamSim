/**
 * 
 */
package de.haw.teamsim.sim;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.haw.teamsim.agent.Agent;
import de.haw.teamsim.agent.Belief;

/**
 * The Team is the framework for agents to know about other team members availability and to broadcast information to active team members.
 * 
 * @author pascal
 *
 */
public class Team extends Observable {
	public enum Status {active, suspended}

	List<Agent> teammembers;
	
	public Team(){
		teammembers = new LinkedList<Agent>();
	}
	
	public void sendInfo2Team(Belief b){
		setChanged();
		notifyObservers(b);
	}
	
	// overriding to add extra logic
	public void deleteObserver(Observer o){
		setChanged();
		super.deleteObserver(o);
		Agent a;
		if(o instanceof Agent){
			a = (Agent)o;
			teammembers.remove(a);
			notifyObservers(new TeamStatusUpdate(a, Status.suspended));
		}
	}
	
	// overriding to add extra logic
	public void addObserver(Observer o){
		setChanged();
		Agent a;
		if(o instanceof Agent){
			a = (Agent)o;
			teammembers.add(a);
			notifyObservers(new TeamStatusUpdate(a, Status.active));
		}
		super.addObserver(o);
	}
	
	public List<Agent> getTeamMembers(){
		return teammembers;
	}
	
	public String toString(){
		return "Projektteam";
	}
}
