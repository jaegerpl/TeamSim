/**
 * 
 */
package de.haw.teamsim.agent.components;

import de.haw.teamsim.agent.Agent;
import de.haw.teamsim.agent.ICommunication;
import de.haw.teamsim.team.Team;

/**
 * @author pascal
 *
 */
public class Communication implements ICommunication {
	
	private Team team;
	private Agent agent;
	
	public Communication(Team team, Agent agent){
		this.team = team;
		this.agent = agent;
	}
	
	public void speakWith(String recipientRole, String topic, int priority, Object info){
		Agent recipient = team.getRoleAgent(recipientRole);
		float retentiveness = recipient.getRetentiveness();
		if(disturbAgent(retentiveness, priority)){
			recipient.receiveInformation(agent, topic, priority, info);
		}
	}
	
	private boolean disturbAgent(float retentiveness, int topicPrio){
		return true; // TODO implement
	}

	@Override
	public void receiveInformation(Agent sender, String topic, int priority,
			Object info) {
		// TODO Auto-generated method stub
		
	}

}
