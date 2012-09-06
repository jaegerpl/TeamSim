package de.haw.teamsim.team;

import de.haw.teamsim.agent.Agent;
import de.haw.teamsim.team.Team.Status;

public class TeamStatusUpdate {
	
	public final Agent agent;
	public final Status status;
	
	public TeamStatusUpdate(Agent a, Status s){
		agent = a;
		status = s;
	}
	
	public String toString(){
		return "TeamStatusUpdat("+agent+", "+status+")";
	}

}
