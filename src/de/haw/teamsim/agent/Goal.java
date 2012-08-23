/**
 * 
 */
package de.haw.teamsim.agent;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * A <code>Goal</code> is basically a list of conditions (<code>Belief</code>s) that need to be 
 * fulfilled for the goal to be reached. A <code>Goal</code> may have a <code>Plan</code> attached to it,
 * that describes how to <code>Goal</code> is ought to be reached. 
 * 
 * @author pascal
 *
 */
public class Goal implements Observer{
	
	private Plan plan = null;
	private List<Belief> goalConditions;		
	private Integer id;
	
	public Goal(Integer id){
		goalConditions = new LinkedList<Belief>();
		this.id = id;
	}
	
	public void addCondition(Belief b){
			goalConditions.add(b);
	}
	
	public Integer getID(){
		return id;
	}

	@Override
	public void update(Observable obs, Object obj) {
		Belief b;
		if(obs instanceof Belief){
			b = (Belief)obs;
			for(Belief bel : goalConditions){
				if(b.getName().equals(bel.getName())){
					bel.updateFact(b.getFact());
					return;
				}
			}
		}
	}

}
