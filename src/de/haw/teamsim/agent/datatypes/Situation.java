/**
 * 
 */
package de.haw.teamsim.agent.datatypes;

import java.util.LinkedList;
import java.util.List;


/**
 * A situation describes the context of an agent and is something an agent can
 * communicate to other agents. A situation consists of goals, tasks and plans 
 * the agent has, together with the beliefs the agent has attached to the situation. 
 * If an agent communicates a situation to another agent, the receiving agent 
 * adopts the beliefs of the communicating agent into its belief base and notes 
 * the tasks and goals of that agent.
 * 
 * @author pascal
 *
 */
public class Situation {
	
	private List<Belief> beliefs;
	private List<Goal> goals;
	private List<Task> tasks;
	private List<Plan> plans;
	
	public Situation(){
		plans = new LinkedList<Plan>();
		beliefs = new LinkedList<Belief>();
		goals = new LinkedList<Goal>();
		tasks = new LinkedList<Task>();
	}
	
	public List<Task> getTasks() {
		return tasks;
	}

	public List<Plan> getPlans() {
		return plans;
	}
	
	public List<Belief> getBeliefs() {
		return beliefs;
	}
	
	public void addTask(Task task){
		tasks.add(task);
	}
	
	public void addPlan(Plan plan){
		plans.add(plan);
	}

	public void addBeliefs(Belief belief) {
		beliefs.add(belief);
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void addGoals(Goal goal) {
		goals.add(goal);
	}

//	public boolean equals(Object obj){
//		Situation s;
//		if(obj instanceof Situation){
//			s = (Situation)obj;
//			for(Belief b1 : s.getBeliefs()){
//				if(!this.getBeliefs().contains(b1) ){
//					return false;
//				}
//			}
//			return true;
//		}
//		return false;
//	}
	
}
