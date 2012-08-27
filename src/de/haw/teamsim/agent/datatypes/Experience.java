/**
 * 
 */
package de.haw.teamsim.agent.datatypes;

import java.util.LinkedList;
import java.util.List;


/**
 * @author pascal
 *
 */
public class Experience {
	
	private List<Belief> beliefs;
	private List<Expectation> expectations;
	private List<String> cues;
	private List<Goal> goals;
	private List<Plan> plans;
	private Integer id;
	
	public Experience(Integer id){
		this.id = id;
		expectations = new LinkedList<Expectation>();
		cues = new LinkedList<String>();
		goals = new LinkedList<Goal>();
		plans = new LinkedList<Plan>();
	}

	public void addExpectancies(Expectation expectation) {
		expectations.add(expectation);
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
