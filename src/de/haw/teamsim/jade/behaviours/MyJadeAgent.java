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

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
   This example shows how to implement the initiator role in 
   a FIPA-request interaction protocol. In this case in particular 
   we use an <code>AchieveREInitiator</code> ("Achieve Rational effect") 
   to request a dummy action to a number of agents (whose local
   names must be specified as arguments).
   @author Giovanni Caire - TILAB
 */
public class MyJadeAgent extends Agent {
	private int nResponders;
	static int behaviourChoice = -1;
	
	public MyJadeAgent(){
		
	}
	
	protected void setup() {
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			behaviourChoice = (Integer)args[0];
			System.out.println("behaviourchoice ist "+behaviourChoice);
		}
		
		// Register a presence service to indicate the participation in the team
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("presence");
		sd.setName("presence");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		if(behaviourChoice == 1){
			addMeetingInitiantionBehaviour();
		} else if(behaviourChoice == 0) {
			addMeetingResponseBehaviour();
		} else {
			System.out.println("wrong behaviourChoice = "+behaviourChoice);
		}
	} 
	
	/**
	 * Creates a message starting the MeetingInitiationProtocol and adds the 
	 * <code>MeetingInitiationBehaviour</code> to the agent's behaviours.
	 */
	private void addMeetingInitiantionBehaviour(){
		AID topic =null;
		String topicname = "Statusmeeting";
		// create meeting Topic
		TopicManagementHelper topicHelper;
		try {
			topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
			topic = topicHelper.createTopic(topicname);
		} catch (ServiceException e) {
			System.out.println("Topic creation of Topic "+topicname+" failed. "+e);
		}
		
		AID[] agents = findPresentAgents();
	  	if (agents != null && agents.length > 0) {
	  		nResponders = agents.length;
	  		System.out.println("Requesting initiation to "+nResponders+" responders.");
	  		
	  		// Fill the REQUEST message
	  		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	  		for (int i = 0; i < agents.length; ++i) {
	  			msg.addReceiver(agents[i]);
	  		}
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			msg.setContent("meeting"); 
			try {
				msg.setContentObject(topic);
			} catch (IOException e) {
				System.out.println("Adding ContentObject to Message failed "+ e);
			}
			
			addBehaviour(RequestMeetingBehaviour.createBehaviour(this, msg, nResponders) );
	  	}
	  	else {
	  		System.out.println("No responder specified.");
	  	}
	}
	
	/**
	 * adds a MeetingRepsonseBehaviour to the agent's list of behaviours.
	 */
	private void addMeetingResponseBehaviour(){
  		// Fill the REQUEST message
  		MessageTemplate template = MessageTemplate.and(
  		  		MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
  		  		MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
  		  						    MessageTemplate.MatchContent("meeting")));
		
		addBehaviour(RequestMeetingResponseBehaviour.createBehaviour(this, template) );
	}
  
  /**
   * Returns an array with all agents that registered the service "presence" with the DF
   * @return
   */
	private AID[] findPresentAgents(){
		
	  /**
	   * AMSAgentDescription [] agents = null;
	   *	try {
       *   SearchConstraints c = new SearchConstraints();
       *   c.setMaxResults (new Long(-1));
       *	agents = AMSService.search( this, new AMSAgentDescription (), c );
       *	}
       *	catch (Exception e) {
       *   System.out.println( "Problem searching AMS: " + e );
       *   e.printStackTrace();
       *	}
       *
       *	for (int i=0; i<agents.length;i++){
       *		AID agentID = agents[i].getName(); }
	   */
	  	  
	  AID[] presentAgents = null;
	  DFAgentDescription template = new DFAgentDescription();
	  ServiceDescription sd = new ServiceDescription();
	  sd.setType("presence");
	  template.addServices(sd);
	  try {
		  DFAgentDescription[] result = DFService.search(this, template);
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
	
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Printout a dismissal message
		System.out.println("Agentt "+getAID().getName()+" terminating.");
		}
}


