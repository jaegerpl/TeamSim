/**
 * 
 */
package de.haw.teamsim.agent.components;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
	
	HashMap<String, Belief> beliefs;
	HashMap<Integer, Goal> suspendedGoals;
	Goal activeGoal;
	List<Situation> situations;
	List<Task> tasks;
	List<Plan> plans;
	
	public Blackboard(){
		beliefs = new HashMap<String, Belief>();
		suspendedGoals = new HashMap<Integer, Goal>();
		situations = new LinkedList<Situation>();
		plans = new LinkedList<Plan>();
		tasks = new LinkedList<Task>();
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
		if(!suspendedGoals.containsKey(g.getID())){
			suspendedGoals.put(g.getID(), g);
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
	public Goal setActiveGoal(Goal g, Boolean dropOldGoal){
		Goal oldGoal = activeGoal;
		activeGoal = g;
		if(!dropOldGoal){
			suspendedGoals.put(oldGoal.getID(), oldGoal);
		}
		return oldGoal;
	}
	
}
