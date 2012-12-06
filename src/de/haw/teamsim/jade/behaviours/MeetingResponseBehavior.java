package de.haw.teamsim.jade.behaviours;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class MeetingResponseBehavior {
	private static Agent agent;
	
	public static AchieveREResponder createBehaviour(Agent a, MessageTemplate template) {

		agent = a;
		return new AchieveREResponder(agent, template) {
			
			protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
				System.out.println("Agent "+agent.getLocalName()+": REQUEST received from "+request.getSender().getName()+". Action is "+request.getContent());
				if (checkAction()) {
					// We agree to perform the action. Note that in the FIPA-Request
					// protocol the AGREE message is optional. Return null if you
					// don't want to send it.
					System.out.println("Agent "+agent.getLocalName()+": Agree");
					ACLMessage agree = request.createReply();
					agree.setPerformative(ACLMessage.AGREE);
					return agree;
				}
				else {
					// We refuse to perform the action
					System.out.println("Agent "+agent.getLocalName()+": Refuse");
					throw new RefuseException("check-failed");
				}
			}
			
			protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
				if (performAction()) {
					System.out.println("Agent "+agent.getLocalName()+": Action successfully performed");
					ACLMessage inform = request.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					return inform;
				}
				else {
					System.out.println("Agent "+agent.getLocalName()+": Action failed");
					throw new FailureException("unexpected-error");
				}	
			}
		};
  }
  
  private static boolean checkAction() {
	  // calculate wether the receiver will draw its attention to the sender
	  // based on the senders team function and the receivers tasks
	  return (Math.random() > 0.2);
  }
  
  private static boolean performAction() {
  	// Simulate action execution by generating a random number
  	return (Math.random() > 0.2);
  }
}
