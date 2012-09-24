package de.haw.teamsim.experiment;

public class ExpAction {

	private static int ExpActionID;
	
	int ID;
	int priority;
	int duration;
	int predecessor; // Vorgänger
	
	public ExpAction(int prio, int duration, int pre){
		ID = ExpActionID++;
		this.priority = prio;
		this.duration = duration;
		this.predecessor = pre;
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

	public void execute(){
		//TODO implement
	}
}
