/**
 * 
 */
package de.haw.teamsim.agent.components;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import de.haw.teamsim.agent.datatypes.Belief;
import de.haw.teamsim.agent.datatypes.Goal;
import de.haw.teamsim.agent.datatypes.Plan;
import de.haw.teamsim.agent.datatypes.Situation;
import de.haw.teamsim.agent.datatypes.Task;

/**
 * The blackboard stores data that is currently important for the agent. To support situation awareness for other agents the blackboard stores situations.
 * The blackboard stores the agents active goals (active and passive) and can hold experiences the agent has matched to the current situation.
 * @author pascal
 *
 */

public class Blackboard implements Runnable {
	
	private HashMap<String, Belief> beliefs;
//	private HashMap<Integer, Goal> suspendedGoals;
//	private Goal activeGoal;
	private List<Situation> situations;
	private List<Task> tasks;
	private List<Plan> plans;
	private PriorityQueue<Goal> goals;
	
	public Blackboard(){
		beliefs = new HashMap<String, Belief>();
//		suspendedGoals = new HashMap<Integer, Goal>();
		situations = new LinkedList<Situation>();
		plans = new LinkedList<Plan>();
		tasks = new LinkedList<Task>();
		goals = new PriorityQueue<Goal>(0, new Comparator<Goal>() {
			// write TestCase for ordering
			@Override
			public int compare(Goal g1, Goal g2) {
				if(g1.getPriority() < g2.getPriority()){
					return -1;
				} else if(g1.getPriority() > g2.getPriority()){
					return 1;
				} else {
					return 0;
				}
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub	
	}
	
	public void updateBelief(String name, Object fact){
		if(beliefs.containsKey(name)){
			beliefs.get(name).setFact(fact);
		} else {
			beliefs.put(name, new Belief(name, fact));
		}
	}
	
	public void addGoal(Goal g){
		if(!goals.contains((g.getID()))){
			goals.add(g);
		}
	}
	
	public void addSituation(Situation s){
		if(!situations.contains(s)){
			situations.add(s);
		}
	}

	/**
	 * Sets the goal g as active goal in the blackboard.
	 * If dropOldGoal is true, the old goal is dropped, otherwise is added to the suspended goals
	 * 
	 * @param g the new active goals
	 * @param dropOldGoal true to drop the old goal, false suspendes the old goal
	 * @return the old active goal
	 */
//	public Goal setActiveGoal(Goal g, Boolean dropOldGoal){
//		Goal oldGoal = activeGoal; 
//		activeGoal = g;
//		if(!dropOldGoal){
//			suspendedGoals.put(oldGoal.getID(), oldGoal);
//		}
//		return oldGoal;
//	}
	
	/**
	 * Chooses a goal to process it one step further.
	 * This method can be extracted to implement different scheduling algorithms (like round robin, fifo or highest prio first)
	 * This one is a highest priority first.
	 */
	public void workOnGoal(){
		
	}
	
}
