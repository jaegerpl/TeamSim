/**
 * 
 */
package de.haw.teamsim.agent.ekb;

import java.util.LinkedList;
import java.util.List;


import de.haw.teamsim.agent.Belief;
import de.haw.teamsim.agent.Goal;
import de.haw.teamsim.agent.Plan;

/**
 * @author pascal
 *
 */
public class Experience {
	
	private List<Belief> beliefs;
	private List<String> expectancies;
	private List<String> cues;
	private List<Goal> goals;
	private List<Plan> plans;
	private Integer id;
	
	public Experience(Integer id){
		this.id = id;
		expectancies = new LinkedList<String>();
		cues = new LinkedList<String>();
		goals = new LinkedList<Goal>();
		plans = new LinkedList<Plan>();
	}

	public void addExpectancies(String expectancie) {
		expectancies.add(expectancie);
	}

	public void addCues(String cue) {
		cues.add(cue);
	}

	public void addGoals(Goal goal) {
		goals.add(goal);
	}

	public void addPlans(Plan plan) {
		plans.add(plan);
	}

	public List<Belief> getBeliefs(){
		return beliefs;
	}
	
	public Integer getID(){
		return id;
	}
	

}
