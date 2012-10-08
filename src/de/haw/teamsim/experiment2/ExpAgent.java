/**
 * 
 */
package de.haw.teamsim.experiment2;

import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import de.haw.teamsim.experiment2.agent.MentalModel;
import de.haw.teamsim.experiment2.sim.ExpSim;
import de.haw.teamsim.experiment2.sim.Message;

/**
 * @author pascal
 *
 */
public abstract class ExpAgent implements Steppable {
	
	protected ExpAgent agent_A;
	protected ExpAgent agent_B;
	protected boolean submit = false;
	/** if agent may submit an agent */
	protected ExpSim sim = null;
	protected String name;
	protected Stoppable stoppable;
	protected MentalModel memo;
	
	
	public ExpAgent(String name){
		this.name = name;
		memo = new MentalModel();
	}
	
	public void addAgents(ExpAgent a, ExpAgent b){
		agent_A = a;
		agent_B = b;
	}
	
	public void addAction(ExpAction action){
		action.setOwner(this);
		memo.addAction(action);
		System.out.println("Agent "+name+": Action with ID: "+action.getID()+" added - 1");
	}
	
	public void addActions(List<ExpAction> action){
		for(ExpAction ac : action){
			ac.setOwner(this);
			memo.addAction(ac);
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
