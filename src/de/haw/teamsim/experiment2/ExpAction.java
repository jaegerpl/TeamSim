package de.haw.teamsim.experiment2;

import de.haw.teamsim.experiment2.sim.ExpSim;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;


public class ExpAction implements Steppable{

	private static int ExpActionID = 1;
	
	protected int ID;
	private int priority;
	private int duration;
	private int predecessor;
	private int successor;
	private boolean execute  =false;
	private ExpSim sim;
	private ExpAgent owner;
	private boolean finished = false;

	private Stoppable stoppable;
	
	public ExpAction(int prio, int duration, int pre, int succ){
		ID = ExpActionID++;
		this.priority = prio;
		this.duration = duration;
		this.predecessor = pre;
		this.successor = succ;
		this.owner = null;
	}

	public void setPredecessorID(int predecessor) {
		this.predecessor = predecessor;
	}

	public void setSuccessorID(int successor) {
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

	/**
	 * Returns the ID of the predecessor
	 * @return
	 */
	public int getPredecessorID() {
		return predecessor;
	}
	/**
	 * Returns the ID of the successor
	 * @return
	 */
	public int getSuccessorID() {
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
		return new String("(ID: "+ID+", P: "+predecessor+", S: "+successor+")\n");
	}

	@Override
	public void step(SimState state) {
		if(execute){
//			System.out.println("Action "+ID+" in execution");
			duration -=1;
		}
		if(duration == 0){
			execute  = false;
			finished = true;
			System.out.println("Action "+ID+" finshed execution");
			stoppable.stop();
//			sim.startNextRound();
		}
		
	}

	public void setStoppanble(Stoppable stop) {
		stoppable = stop;		
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public boolean equals(Object o){
		if(o instanceof ExpAction){
			ExpAction a = (ExpAction)o;
			return this.ID == a.getID();
		}
		return false;
		
	}
}
