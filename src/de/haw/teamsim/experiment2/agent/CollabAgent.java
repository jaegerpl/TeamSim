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
	private boolean hasFirstAction = false;
	private MessageFactory msgFactory;
	private List<Message> incomingMessages;				// the inbox
	enum nextStep {check, execute, add, remove, iHave, executed, inform};
	boolean listen, say, check, execute;				// state variables
	private Message out;								// the message to be send during the next step
	private ExpAction action;							// the action to be handled during the next step
	private ExpAction checkAction;						// long time store for action that needs to be check after stepCount steps
	private nextStep todo;								// the next step to be taken
	private int checkCounter = 0;						// after how many steps something should be checked
	private boolean checkCount = false;					// if true the checkCounter will be increased each step
	private boolean output = false;
	
	
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
		
		if(output){
		System.out.println("Agent "+name+"\n"
				+"firststep = "+firstStep+"\n"
				+"hasFirstActon = "+hasFirstAction+"\n"
				+"listen = "+listen+"\n"
				+"say = "+say+"\n"
				+"check = "+check+"\n"
				+"execute = "+execute+"\n"
				+"action = "+action+"\n"
				+"checkAction = "+checkAction+"\n"
				+"todo = "+todo+"\n"
				+"checkCount = "+checkCount+"\n"
				+"checkCounter = "+checkCounter+"\n"
				+"===================");
		}
		
		
		if(initialized){
			if(checkCount){
				checkCounter++;
				if(checkCounter == 10){
					check = true;
				}
			}
			if(firstStep){
				firstStep =  false;
				System.out.println("Agent "+name+" has actions: "+memo.getActions());
				hasFirstAction = check4FirstAction();
				listen = true;
			} 
			
			if(hasFirstAction){
				output = true;
				// tell other
				// submit for execution
				Message msg = msgFactory.informMessage(sim.Team, Message.ownedBy);
				msg.setActionID(action.getID());
				sim.msgSys.sendMessage(msg);
				hasFirstAction =false;
				listen = false;
				todo = nextStep.execute;
				execute = true;
			} 
			
			if(check){
				check = false;
				if(checkAction.isFinished()){	// action is finished
					check = false;
					checkCount = false;
					checkAction = null;
					checkCounter = 0;
				} else {
					checkCounter = checkCounter -3; // check again in 3 steps
				}			
			} else if (execute) {
				execute= false;
				switch (todo) {
				case add:
					addAction(action);  // check if contains already
					action = null;
					todo = null;
					listen = true;
					break;
				case remove:
					memo.remove(action.getID());
					action = null;
					todo = null;
					listen = true;
					break;
				case execute:
					sim.submitForExecution(action, this);
					say = true;
					break;
				default:
					System.err.println("In Agent "+name+" in step case EXECUTE this shoudl not happen");
					break;
				}
			} else if (say) {
				say = false;
				switch (todo) {
				case iHave:
					sim.msgSys.sendMessage(out);
					action = null;
					todo = null;
					listen = true;
					break;
				case execute:
					checkAction = action;
					checkCount = true; 
					action = null;
					todo = null;
					listen = true;
					break;
				case executed:
					checkAction = null;
					todo = null;
					listen = true;
					break;
				case inform:
					// pick an action and tell others, that you have it
					System.out.println("Agent "+name+" needs to say something");
					say = false;
					todo = null;
					listen = true;
					break;
				default:
					System.err.println("In Agent "+name+" in step case SAY this shoudl not happen");
					break;
				}			
			} else if(listen){
				listen = false;
				readInbox();	
			}
		}
	}
	
	private void readInbox() {
		if(!incomingMessages.isEmpty()){
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
		} else {
			say = true;
			todo = nextStep.inform;
		}
	}
	
	private boolean check4FirstAction() {
		for(ExpAction a : memo.getActions()){
			if(a.getPredecessorID() == 0){
				action = a;
				return true;
			}
		}
		return false;
	}

	public void receiveMsg(Message msg){
		incomingMessages.add(msg);
		System.out.println("Agent "+name+" message received");
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
