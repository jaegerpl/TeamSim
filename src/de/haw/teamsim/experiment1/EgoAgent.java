/**
 * 
 */
package de.haw.teamsim.experiment1;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import sim.engine.SimState;

/**
 * @author pascal
 *
 */
public class EgoAgent extends ExpAgent {
	
	private int submitIndex = 0; // the index of the next action to submit 
	private boolean firstStep = true;

	public EgoAgent(String name) {
		super(name);
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

}
