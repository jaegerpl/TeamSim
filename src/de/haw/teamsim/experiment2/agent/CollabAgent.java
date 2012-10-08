/**
 * 
 */
package de.haw.teamsim.experiment2.agent;

import java.util.LinkedList;
import java.util.List;

import de.haw.teamsim.experiment2.ExpAction;
import de.haw.teamsim.experiment2.ExpActionTemplate;
import de.haw.teamsim.experiment2.ExpAgent;
import de.haw.teamsim.experiment2.sim.Message;
import de.haw.teamsim.experiment2.sim.MessageFactory;

import sim.engine.SimState;

/**
 * @author pascal
 *
 */
public class CollabAgent extends ExpAgent {
	 
	private boolean firstStep = true;
	private boolean checkOwnActions = false;
	private boolean hasFirstAction = false;
	private boolean hasMessages = false;
	private boolean doSomenthing = false;
	private int stepcount = 0;
	private ExpAction submitAction = null;
	private MessageFactory msgFactory;
	private List<Message> incomingMessages;
	enum nextStep {check, execute, add, remove, iHave};
	boolean listen, say, check, execute;
	Message out;
	ExpAction action;
	nextStep todo;
	
	
	public CollabAgent(String name) {
		super(name);
		incomingMessages = new LinkedList<Message>();
		msgFactory = new MessageFactory(this.name);
	}
	
	/* (non-Javadoc)
	 * @see de.haw.teamsim.experiment.ExpAgent#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		stepcount++;
		if(firstStep){
			firstStep =  false;
			System.out.println("Agent "+name+" has actions: "+memo.getActions());
			checkOwnActions = true;
		} else if(checkOwnActions){
			checkOwnActions = false;
			hasFirstAction = check4FirstAction();			
		} 
		
		hasMessages = !incomingMessages.isEmpty();
		if(hasFirstAction){
			// tell other
			// submit for execution
			Message msg = msgFactory.informMessage(sim.Team, Message.ownedBy);
			msg.setActionID(submitAction.getID());
			sim.msgSys.sendMessage(msg);
			
			sim.submitForExecution(submitAction, this);
			
			hasFirstAction =false;
		} 
		
		
		if(listen){
			listen = false;
			// read Message
			readInbox();	
		} else if(check){
			
			check = false;
		} else if (execute) {
			execute= false;
			switch (todo) {
			case add:
				addAction(action);  // check if contains already
				break;
			case remove:
				memo.remove(action.getID());
				break;
			case execute:
				sim.submitForExecution(action, this);
				say = true;
			default:
				break;
			}
			
			check = true;
		} else if (say) {
			sim.msgSys.sendMessage(out);			
			listen = true;
		}

				stepcount = 0;
				boolean submitSuccess = false;
				if(!sim.isExecuting()){
					ExpAction action = memo.getFirstAction();
					if(!(action instanceof ExpActionTemplate)){ // i.e. if it is owned by this agent
						submitSuccess = sim.submitForExecution(action, this);
						if(submitSuccess){
							out = msgFactory.informMessage(sim.Team, Message.executed);
							out.setActionID(action.getID());
							sim.msgSys.sendMessage(out);
						} else {
							Message msg = msgFactory.informMessage(sim.Team, Message.ownedBy);
							msg.setActionID(action.getID());
							sim.msgSys.sendMessage(msg);
						}
					}
				} else {
					ExpAction action = memo.getFirstAction();
					if(!(action instanceof ExpActionTemplate)){ // i.e. if it is owned by this agent
						Message msg = msgFactory.informMessage(sim.Team, Message.ownedBy);
						msg.setActionID(action.getID());
						sim.msgSys.sendMessage(msg);
					}
				}
			}
		}
	}
	
	private void readInbox() {
		Message msg = incomingMessages.remove(0);
		if(msg.getType() == Message.Type.INFORM){
			if(msg.getContent() == Message.ownedBy){
				action = new ExpActionTemplate(msg.getActionID());
				todo = nextStep.add;					
			} else if (msg.getContent() == Message.executed){
				action = new ExpActionTemplate(msg.getActionID());
				todo = nextStep.remove;					
			}
			execute = true;
		} else { // REQUEST
			if(msg.getContent() == Message.whoHas){
				ExpAction a = getActionWithID(msg.getActionID());
				if(!(a instanceof ExpActionTemplate)){
					action = a;
					say = true;
					todo = nextStep.iHave;
					out = msgFactory.informMessage(msg.getSender(), Message.ownedBy);
					out.setActionID(a.getID());
					say = true;
				}
			} else if (msg.getContent() == Message.plzExecute){
				ExpAction a = getActionWithID(msg.getActionID());
				if(!(a instanceof ExpActionTemplate)){
					action = a;
					todo = nextStep.execute;
					
					out = msgFactory.informMessage(sim.Team, Message.executed);
					out.setActionID(a.getID());	
					execute = true;
				}
			}
		}
	}
	
	private boolean check4FirstAction() {
		for(ExpAction a : memo.getActions()){
			if(a.getPredecessorID() == 0){
				submitAction = a;
				return true;
			}
		}
		return false;
	}

	public boolean receiveMsg(Message msg){
		incomingMessages.add(msg);
		System.out.println("Agent "+name+" message received");
		return false;
	}
	
	private ExpAction getActionWithID(int id){
		for(ExpAction a : memo.getActions()){
			if(a.getID() == id){
				return a;
			}
		}
		return null;
	}
}
