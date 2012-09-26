/**
 * 
 */
package de.haw.teamsim.experiment;

import sim.engine.SimState;

/**
 * @author pascal
 *
 */
public class EgoAgent extends ExpAgent {

	/* (non-Javadoc)
	 * @see de.haw.teamsim.experiment.ExpAgent#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
	
		if(submit){
			ExpAction action = null; // TODO get action from somewhere
			sim.submitForExecution(action, this);
			submit = false;
		}
		// TODO Auto-generated method stub
	}

}
