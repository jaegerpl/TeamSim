/**
 * 
 */
package de.haw.teamsim.experiment;

import java.util.Collections;
import java.util.Comparator;
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
	
	private List<Message> incomingMessages;

	public CollabAgent(String name) {
		super(name);
		incomingMessages = new LinkedList<Message>();
	}

	private List<ExpAction> myActions = new LinkedList<ExpAction>();
	private  boolean submitSuccess = false;
	
	/* (non-Javadoc)
	 * @see de.haw.teamsim.experiment.ExpAgent#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		if(firstStep){
			firstStep = false;
			
			myActions.addAll(actions);
			Collections.sort(myActions, new Comparator<ExpAction>() {
	
				/** Sort by Priority 	 */
				@Override				
				public int compare(ExpAction a1, ExpAction a2) {
					return a1.getPriority() - a2.getPriority();
				}
			});
			System.out.println("Agent "+name+": Actions sorted according to Prio");
		} 
		
		if(!incomingMessages.isEmpty()){
			Message msg = incomingMessages.get(0);
			if(msg.getType() == Message.Type.INFORM){
				if(msg.getContent() == Message.ownedBy){
					ExpActionTemplate actiontmp = new ExpActionTemplate(msg.getActionID());
					if(myActions.contains(actiontmp.getID()){
						
					} else {
						myActions.add(actiontmp);
						// TODO sort according to sequenz?
					}
				} else if (msg.getContent() == Message.executed){
					myActions.remove(msg.getAction());				
				} else {
					// error
				}
			} else { // REQUEST
				if(msg.getContent() == Message.whoHas){
					
				} else if (msg.getContent() == Message.plzExecute){
					
				} else {
					// error
				}				
			}
		}
	
		if(submit){
			ExpAction action = myActions.get(submitIndex);
			submitSuccess = sim.submitForExecution(action, this);
			if(submitSuccess){
				myActions.remove(action);
				submitIndex = 0;  // start again with highest prio action
			} else {
				submitIndex += 1;
				if(submitIndex == myActions.size()){
					submitIndex = 0;
				}
				
			}
			submit = false;
		}else if(myActions.isEmpty()) {
				stoppable.stop();
				System.out.println("Agent "+name+" IS DONE");
		} else {
			System.out.println("Agent "+name+" do nothing");
		}
	}
	
	public boolean receiveMessage(Message msg){
		incomingMessages.add(msg);
		return false;
	}

}
