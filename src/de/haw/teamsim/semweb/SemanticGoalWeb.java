/**
 * 
 */
package de.haw.teamsim.semweb;

import java.util.HashMap;
import java.util.Map;

import de.haw.teamsim.semweb.SemanticGoalWeb.Predicate;

/**
 * 
 * TODO: remove logic, to keep the web clean and minimal
 * 
 * @author pascal
 *
 */
public class SemanticGoalWeb {
	
	public enum Predicate{
		isSubGoal, 				// all conditions are also in the main goal
		extendsGoal,			// some conditions are in the main goal, others are not
		isMainGoal, 			// back direction of isSubGoal and extendsGoal
		isConflictaryWith, 		// means that one or more conditions are conflictary
		isResponsibleFor, 		// Points to the agent responsible for the goal
		belongsToArea,			// the area can be used to filter the visibility
		responsibleRole			// The AgentRole that typically has the needed skills for a goal 
	}
	
	private Map<Integer, Node> web;
	
	public SemanticGoalWeb(){
		web = new HashMap<Integer, Node>();
	}
	
	public boolean contains(Node n){
		if(web.containsKey(n.getID())){
			return true;
		}
		return false;
	}

	public void add(Node n) {
		web.put(n.getID(), n);
	}

	/**
	 * Returns all Nodes that fulfill the given predicate
	 * @param pred
	 */
	public void getObjects(Predicate pred){
		// TODO Auto-generated method stub
		
		
	}

}
