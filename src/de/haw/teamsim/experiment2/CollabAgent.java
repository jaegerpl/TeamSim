/**
 * 
 */
package de.haw.teamsim.experiment2;

import java.util.LinkedList;
import java.util.List;

import sim.engine.SimState;

/**
 * @author pascal
 *
 */
public class CollabAgent extends ExpAgent {
	
	private int submitIndex = 0; // the index of the next action to submit 
	private boolean firstStep = true;
	private boolean checkOwnActions = false;
	private boolean hasFirstAction = false;
	private boolean hasMessages = false;
	private boolean doSomenthing = false;
	private ExpAction submitAction = null;
	private MessageFactory msgFactory;
	
	private List<Message> incomingMessages;

	public CollabAgent(String name) {
		super(name);
		incomingMessages = new LinkedList<Message>();
		msgFactory = new MessageFactory(this);
	}

	private List<ExpAction> myActions = new LinkedList<ExpAction>();
	private  boolean submitSuccess = false;
	
	/* (non-Javadoc)
	 * @see de.haw.teamsim.experiment.ExpAgent#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		if(firstStep){
			firstStep =  false;
			sortActions();
			checkOwnActions = true;
		} else if(checkOwnActions){
			checkOwnActions = false;
			hasFirstAction = check4FirstAction();			
		} 
		
		hasMessages = incomingMessages.isEmpty();
		if(hasFirstAction){
			// tell other
			// submit for execution
			Message msg = new Message(Message.Type.INFORM);
			msg.setActionID(submitAction.getID());
			msg.setReceiver(agent_A);
			msg.setContent(Message.ownedBy);
			agent_A.receiveMsg(msg);
			msg.setReceiver(agent_B);
			agent_B.receiveMsg(msg);
			
			sim.submitForExecution(submitAction, this);
			
			hasFirstAction =false;
		} else {
			if(hasMessages){
				Message msg = incomingMessages.get(0);
				if(msg.getType() == Message.Type.INFORM){
					if(msg.getContent() == Message.ownedBy){
						ExpActionTemplate actiontmp = new ExpActionTemplate(msg.getActionID());
						if(myActions.contains(actiontmp)){
							// TODO check for corrupt data, i.e. compare action owners
						} else {
							addAction(actiontmp);
						}
					} else if (msg.getContent() == Message.executed){
						myActions.remove(msg.getActionID());				
					} else {
						// error
					}
				} else { // REQUEST
					if(msg.getContent() == Message.whoHas){
//						ExpActionTemplate action = new ExpActionTemplate(msg.getActionID());
						
						ExpAction a = getActionWithID(msg.getActionID());
						if(!(a instanceof ExpActionTemplate)){
							Message out = msgFactory.informMessage(msg.getSender(), Message.ownedBy);
							a.getOwner().receiveMsg(out);
						}
					} else if (msg.getContent() == Message.plzExecute){
						ExpAction a = getActionWithID(msg.getActionID());
						if(!(a instanceof ExpActionTemplate)){
							sim.submitForExecution(a, this);
							Message out = msgFactory.informMessage(msg.getSender(), Message.executed);
							agent_A.receiveMsg(out);
							agent_B.receiveMsg(out);
						}
					} else {
						// error
					}				
				}
			} else if(doSomenthing){
				// react
				// answer
				// submit action
			}
		}
		
		
		if(!incomingMessages.isEmpty()){
			
		}
	
//		if(submit){
//			ExpAction action = myActions.get(submitIndex);
//			submitSuccess = sim.submitForExecution(action, this);
//			if(submitSuccess){
//				myActions.remove(action);
//				submitIndex = 0;  // start again with highest prio action
//			} else {
//				submitIndex += 1;
//				if(submitIndex == myActions.size()){
//					submitIndex = 0;
//				}
//				
//			}
//			submit = false;
//		}else if(myActions.isEmpty()) {
//				stoppable.stop();
//				System.out.println("Agent "+name+" IS DONE");
//		} else {
//			System.out.println("Agent "+name+" do nothing");
//		}
	}
	
	private boolean check4FirstAction() {
		for(ExpAction a : myActions){
			if(a.getPredecessor() == 0){
				submitAction = a;
				return true;
			}
		}
		return false;
	}

	private void sortActions() {
		for(ExpAction a : actions){
			addActionInSequence(a);
		}
		System.out.println("Agent "+name+": Actions sorted according to sequence");
		System.out.println("Agent "+name+" Sequence "+myActions.toString());
	}

	private void addActionInSequence(ExpAction a) {
		if(!myActions.isEmpty()){
			for(ExpAction b : myActions){
				if(b.getPredecessor() ==a.getID()){
					int i = myActions.indexOf(b);
					if(i == 0){ i = 1;}
					myActions.add(i-1, a);
					break;
				} else if(b.getSuccessor() == a.getID()){
					int i = myActions.indexOf(b);
					if(i == myActions.size()-1){
						i = i-1;
					}
					myActions.add(i+1, a);
					break;
				} 
			}
		} else {
			myActions.add(a);
		}		
	}

	public boolean receiveMsg(Message msg){
		incomingMessages.add(msg);
		System.out.println("Agent "+name+" message received");
		return false;
	}
	
	private ExpAction getActionWithID(int id){
		
	}
}
