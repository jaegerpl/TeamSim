/**
 * 
 */
package de.haw.teamsim.semweb;

import de.haw.teamsim.semweb.SemanticGoalWeb.Predicate;

/**
 * A Link is the predicate used in semantic webs.
 * A Link points from an Subject (a Node) to an Object (again a Node).
 *  
 * @author pascal
 *
 */
public class Link {
	Predicate predicate;
	Node object;

	public Link(Predicate pred, Node obj){
		predicate = pred;
		object = obj;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public Node getObject() {
		return object;
	}
	
}

