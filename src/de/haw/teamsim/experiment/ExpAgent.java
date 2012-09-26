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
public abstract class ExpAgent implements Steppable {
	
	protected Set<ExpAction> actions;
	protected ExpAgent agent_A;
	protected ExpAgent agent_B;
	protected boolean submit = false;
	protected ExpSim sim = null;
	
	
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
	public abstract void step(SimState state);

	/**
	 * Called when the simulation has chosen another action to be executed.
	 * Take this action again into consideration for next submission.
	 * @param ac
	 */
	public void notifyActionRejected(ExpAction ac) {
		addAction(ac);
	}

}
