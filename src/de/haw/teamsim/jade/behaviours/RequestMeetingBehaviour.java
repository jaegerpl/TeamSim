package de.haw.teamsim.jade.behaviours;

import java.util.Vector;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class RequestMeetingBehaviour {
	
	private static int nResponders = 0;
	private static Agent agent;
	
	public static AchieveREInitiator createBehaviour(Agent a, ACLMessage msg, int responders) {
		
		agent = a;
		nResponders = responders;
		return new AchieveREInitiator(a, msg){

			protected void handleInform(ACLMessage inform) {
				System.out.println("Agent "+inform.getSender().getName()+" successfully joined the meeting");
			}
			protected void handleRefuse(ACLMessage refuse) {
				System.out.println("Agent "+refuse.getSender().getName()+" refused to to join the meeting");
				nResponders--;
			}
			protected void handleFailure(ACLMessage failure) {
				if (failure.getSender().equals(myAgent.getAMS())) {
					// FAILURE notification from the JADE runtime: the receiver
					// does not exist
					System.out.println("Responder does not exist");
				}
				else {
					System.out.println("Agent "+failure.getSender().getName()+" failed to perform the requested action");
				}
			}
			protected void handleAllResultNotifications(Vector notifications) {
				if (notifications.size() >= nResponders*0.9) {
					agent.addBehaviour(null);
				} else {
					// skip meeting?
				}
			}
		};
	}
}
