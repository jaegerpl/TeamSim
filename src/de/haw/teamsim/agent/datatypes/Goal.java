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
public class Goal implements Observer{
	
	private static int ID = 0;
	private Plan plan = null;
	private List<Belief> conditions;		
	private Integer id;
	private float priority;
	private boolean achieved;	
	
	public Goal(){
		conditions = new LinkedList<Belief>();
		this.id = ++ID;	
		priority = 0;
	}
	
	public void addCondition(Belief b){
			conditions.add(b);
			b.addObserver(this);
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

	@Override
	public void update(Observable obs, Object obj) {
		Belief b;
		if(obs instanceof Belief){
			b = (Belief)obs;
			for(Belief bel : conditions){
				if(b.getName().equals(bel.getName())){
					bel.setFact(b.getFact());
					return;
				}
			}
		}
	}
	
	public boolean isAchieved(){		
		return achieved;
	}
	
	private void checkGoalAchieved(){
		
	}
	
	private class Condition{
		
		private Belief belief;
		private boolean achieved;
		
		public Condition(Belief b){
			belief = b;
		}
		
		private void setAchieved(boolean b){
			achieved = b;
		}
		
		private boolean isAchieved(){
			return achieved;
		}
	}

}
