package de.haw.teamsim.jade.behaviours;

/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 * 
 * GNU Lesser General Public License
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Date;
import java.util.Vector;

/**
   This example shows how to implement the initiator role in 
   a FIPA-request interaction protocol. In this case in particular 
   we use an <code>AchieveREInitiator</code> ("Achieve Rational effect") 
   to request a dummy action to a number of agents (whose local
   names must be specified as arguments).
   @author Giovanni Caire - TILAB
 */
public class MeetingInitiatorBehaviour extends Agent {
	private int nResponders;
	private Agent myAgent;
	
	public MeetingInitiatorBehaviour(Agent owner){
		myAgent = owner;
	}
	
	protected void setup() {
	  	// get names of agents present at the container
	  	AID[] agents = findPresentAgents();
	  	if (agents != null && agents.length > 0) {
	  		nResponders = agents.length;
	  		System.out.println("Requesting dummy-action to "+nResponders+" responders.");
	  		
	  		// Fill the REQUEST message
	  		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	  		for (int i = 0; i < agents.length; ++i) {
	  			msg.addReceiver(agents[i]);
//	  			msg.addReceiver(agents[i], AID.ISLOCALNAME);
	  		}
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			// We want to receive a reply in 10 secs
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			// sender requests the attention of receiver, i.e. the following messages 
			// of sender are the only thing the receiver will deal with
			msg.setContent("attention"); 
			
			addBehaviour(new AchieveREInitiator(this, msg) {
				protected void handleInform(ACLMessage inform) {
					System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
				}
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent "+refuse.getSender().getName()+" refused to perform the requested action");
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
					if (notifications.size() < nResponders) {
						// Some responder didn't reply within the specified timeout
						System.out.println("Timeout expired: missing "+(nResponders - notifications.size())+" responses");
					}
				}
			} );
			myAgent.send(msg);
	  	}
	  	else {
	  		System.out.println("No responder specified.");
	  	}
	} 
  
  private AID[] findPresentAgents(){
	  AID[] presentAgents = null;
	  DFAgentDescription template = new DFAgentDescription();
	  ServiceDescription sd = new ServiceDescription();
	  sd.setType("presence");
	  template.addServices(sd);
	  try {
		  DFAgentDescription[] result = DFService.search(myAgent, template);
		  presentAgents = new AID[result.length];
		  for (int i = 0; i < result.length; ++i) {
			  presentAgents[i] = result[i].getName();
		  }
	  }
	  catch (FIPAException fe) {
		  fe.printStackTrace();
	  }
	  return presentAgents;
  }
}


