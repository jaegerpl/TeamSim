/**
 * 
 */
package de.haw.teamsim.semweb;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.haw.teamsim.semweb.SemanticGoalWeb.Predicate;

/**
 * 
 * TODO: remove logic, to keep the web clean and minimal
 * 
 * @author pascal
 *
 */
public class Node {
	
	private Integer ID;
	private Object data;
	private Map<Predicate, List<Node>> links;
	
	public static SemanticGoalWeb theWeb = new SemanticGoalWeb();
	
	public Node(Object data, Integer ID){
		this.data = data;
		this.ID = ID;
		links = new HashMap<SemanticGoalWeb.Predicate, List<Node>>();
	}
	
	public Predicate[] getPredicates(){
		return (Predicate[])links.keySet().toArray();
	}
	
	public List<Node> getObjects(Predicate pred){
		return links.get(pred);
	}
	
	public void addNewLink(Predicate pred, Node obj){
		if(!theWeb.contains(obj)){
			theWeb.add(obj);
		}
		List<Node> list = links.get(pred);
		if(list == null){
			list = new LinkedList<Node>();
			list.add(obj);
			links.put(pred, list);
		} else {
			list.add(obj);
			links.put(pred, list);
		}
	}
	
	public Object getData(){
		return data;
	}
	
	public Integer getID(){
		return ID;
	}

}
