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
	
	
	public ExpAgent(){
	}
	
	public void addAgents(ExpAgent a, ExpAgent b){
		agent_A = a;
		agent_B = b;
	}
	
	public void addAction(ExpAction action){
		actions.add(action);
	}
	
	public void addActions(List<ExpAction> action){
		for(ExpAction ac : action){
			actions.add(ac);
		}
	}


	@Override
	public void step(SimState state) {
		// TODO Auto-generated method stub
		
	}

}
