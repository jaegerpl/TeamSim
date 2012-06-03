/**
 * 
 */
package de.haw.teamsim.agent.blackboard;

import java.util.List;

import de.haw.teamsim.agent.Belief;

/**
 * A situation is a list of believes that describe the situation
 * It might be extended to an experience
 * 
 * @author pascal
 *
 */
public class Situation {
	
	private List<Belief> beliefs;
	
	public Situation(List<Belief> b){
		beliefs = b;
	}
	
	public List<Belief> getBeliefs(){
		return beliefs;
	}
	
	public boolean equals(Object obj){
		Situation s;
		if(obj instanceof Situation){
			s = (Situation)obj;
			for(Belief b1 : s.getBeliefs()){
				if(!this.getBeliefs().contains(b1) ){
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
