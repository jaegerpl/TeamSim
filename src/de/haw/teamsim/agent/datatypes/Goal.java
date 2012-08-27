/**
 * 
 */
package de.haw.teamsim.agent.datatypes;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * A <code>Goal</code> is basically a list of conditions (<code>Belief</code>s) that need to be 
 * fulfilled for the goal to be reached. A <code>Goal</code> may have a <code>Plan</code> attached to it,
 * that describes how the <code>Goal</code> is ought to be reached. 
 * 
 * @author pascal
 *
 */
public class Goal{
	
	private static int ID = 0;
	private Plan plan = null;
	private List<Condition> conditions;		
	private Integer id;
	private float priority;
	private boolean achieved;	
	
	public Goal(){
		conditions = new LinkedList<Condition>();
		this.id = ++ID;	
		priority = 0;
	}
	
	public void addCondition(Belief b){
		if(!hasCondition(b)){
			Condition cond = new Condition(b, this);
			conditions.add(cond);
			b.addObserver(cond);
		}		
	}
	
	public Integer getID(){
		return id;
	}
	
	public void setPriority(float prio){
		priority = prio;
	}
	
	public float getPriority(){
		return priority;
	}
	
	/**
	 * Checks all conditions if the contain the given Belief
	 * 
	 * @param b
	 * @return
	 */
	private boolean hasCondition(Belief b){
		for(Condition c : conditions){
			if(c.belief.equals(b)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isAchieved(){		
		return achieved;
	}
	
	private void checkGoalAchieved(){
		boolean temp = true;
		for(Condition c : conditions){
			if(c.isAchieved() == false){
				temp = false;
			}
		}
		achieved = temp;
	}
	
	/**
	 * A Condition wraps a Belief and adds a status indicating that this condition has been achieved.
	 * 
	 * @author pascal
	 *
	 */
	private class Condition implements Observer{
		
		private Belief belief;
		private Goal goal;
		private boolean achieved;
		
		public Condition(Belief b, Goal g){
			belief = b;
			goal = g;
		}
		
		private void setAchieved(boolean b){
			achieved = b;
		}
		
		private boolean isAchieved(){
			return achieved;
		}

		@Override
		public void update(Observable obs, Object obj) {
			Belief b = (Belief)obs;
			if(belief.getFact().equals(b.getFact())){
				setAchieved(true);
				goal.checkGoalAchieved();
			}
		}
	}

}
