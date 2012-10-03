/**
 * 
 */
package de.haw.teamsim.experiment2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.haw.teamsim.experiment2.sim.ExpSim;
import de.haw.teamsim.experiment2.sim.Message;

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
		System.out.println("Agent "+name+": Action with ID: "+action.getID()+" added - 1");
	}
	
	public void addActions(List<ExpAction> action){
		for(ExpAction ac : action){
			ac.setOwner(this);
			actions.add(ac);
			System.out.println("Agent "+name+": Action with ID: "+ac.getID()+" added - 2");
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
	
	public String toString(){
		return name;
	}

	public void setStoppanble(Stoppable stop) {
		this.stoppable = stop;
	}

	abstract public boolean receiveMsg(Message msg);
	
	public String getName(){
		return name;
	}

}
