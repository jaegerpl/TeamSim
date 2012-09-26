/**
 * 
 */
package de.haw.teamsim.experiment;

import java.util.List;
import java.util.Set;

import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * @author pascal
 *
 */
public class ExpAgent implements Steppable {
	
	private Set<ExpAction> actions;
	private ExpAgent agent_A;
	private ExpAgent agent_B;
	private boolean submit = false;
	private ExpSim sim = null;
	
	
	public ExpAgent(){
	}
	
	public void addAgents(ExpAgent a, ExpAgent b){
		agent_A = a;
		agent_B = b;
	}
	
	public void addAction(ExpAction action){
		action.setOwner(this);
		actions.add(action);
	}
	
	public void addActions(List<ExpAction> action){
		for(ExpAction ac : action){
			ac.setOwner(this);
			actions.add(ac);
		}
	}
	
	public void notifyForNextSubmission(){
		submit = true;
	}
	
	public void setSimulation(ExpSim sim){
		this.sim = sim;
	}


	@Override
	public void step(SimState state) {
		if(submit){
			ExpAction action = null; // TODO get action from somewhere
			sim.submitForExecution(action, this);
			submit = false;
		}
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the simulation has chosen another action to be executed.
	 * Take this action again into consideration for next submission.
	 * @param ac
	 */
	public void notifyActionRejected(ExpAction ac) {
		addAction(ac);
	}

}
