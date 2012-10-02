/**
 * 
 */
package de.haw.teamsim.experiment2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

/**
 * @author pascal
 *
 */
public abstract class ExpAgent implements Steppable {
	
	protected Set<ExpAction> actions;
	protected ExpAgent agent_A;
	protected ExpAgent agent_B;
	protected boolean submit = false;
	/** if agent may submit an agent */
	protected ExpSim sim = null;
	protected String name;
	protected Stoppable stoppable;
	
	
	public ExpAgent(String name){
		actions = new HashSet<ExpAction>();
		this.name = name;
	}
	
	public void addAgents(ExpAgent a, ExpAgent b){
		agent_A = a;
		agent_B = b;
	}
	
	public void addAction(ExpAction action){
		action.setOwner(this);
		actions.add(action);
		System.out.println("Action with ID: "+action.getID()+" added");
	}
	
	public void addActions(List<ExpAction> action){
		for(ExpAction ac : action){
			ac.setOwner(this);
			actions.add(ac);
			System.out.println("Action with ID: "+ac.getID()+" added");
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
	
	public String toString(){
		return name;
	}

	public void setStoppanble(Stoppable stop) {
		this.stoppable = stop;
	}

	abstract public boolean receiveMsg(Message msg);	

}
