package de.haw.teamsim.experiment;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;


public class ExpAction implements Steppable{

	private static int ExpActionID;
	
	private int ID;
	private int priority;
	private int duration;
	private int predecessor;
	private int successor;
	private boolean execute  =false;
	private ExpSim sim;
	private ExpAgent owner;

	private Stoppable stoppable;
	
	public ExpAction(int prio, int duration, int pre, int succ){
		ID = ExpActionID++;
		this.priority = prio;
		this.duration = duration;
		this.predecessor = pre;
		this.successor = succ;
		this.owner = null;
	}

	public void setPredecessor(int predecessor) {
		this.predecessor = predecessor;
	}

	public void setSuccessor(int successor) {
		this.successor = successor;
	}

	public int getID() {
		return ID;
	}

	public int getPriority() {
		return priority;
	}

	public int getDuration() {
		return duration;
	}

	public int getPredecessor() {
		return predecessor;
	}
	public int getSuccessor() {
		return successor;
	}
	
	public void execute(ExpSim sim){
		this.sim = sim;
		execute = true;
		System.out.println("Action "+ID+" set to execute");
	}

	public ExpAgent getOwner() {
		return owner;
	}

	public void setOwner(ExpAgent owner) {
		this.owner = owner;
	}
	
	public String toString(){
		return new String(new Integer(ID).toString());
	}

	@Override
	public void step(SimState state) {
		if(execute){
			System.out.println("Action "+ID+" in execution");
			duration -=1;
		}
		if(duration == 0){
			execute  = false;
			System.out.println("Action "+ID+" finshed execution");
			stoppable.stop();
			sim.startNextRound();
		}
		
	}

	public void setStoppanble(Stoppable stop) {
		this.stoppable = stop;		
	}
}
