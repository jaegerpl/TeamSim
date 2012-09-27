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
public class EgoAgent extends ExpAgent {
	
	private int submitIndex = 0; // the index of the next action to submit 

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
		if(myActions.isEmpty()){
			myActions.addAll(actions);
			Collections.sort(myActions, new Comparator<ExpAction>() {
	
				/**
				 * Sort by Priority
				 * @param a1
				 * @param a2
				 * @return
				 */
				@Override
				
				public int compare(ExpAction a1, ExpAction a2) {
					return a1.getPriority() - a2.getPriority();
				}
			});
//			System.out.println("Agent "+name+": Actions sorted according to Prio");
		} else {
			stoppable.stop();
			System.out.println("Agent "+name+" IS DONE");
		}
	
		if(submit){
			ExpAction action = myActions.get(submitIndex);
			submitSuccess = sim.submitForExecution(action, this);
			if(submitSuccess){
				myActions.remove(action);
				submitIndex = 0;  // start again with highest prio action
			} else {
				if(submitIndex < myActions.size()-1){
					submitIndex += 1;
				} else {
					submitIndex = 0;
				}
				
			}
			submit = false;
		} else {
			System.out.println("Agent "+name+" do nothing");
		}
	}

}
