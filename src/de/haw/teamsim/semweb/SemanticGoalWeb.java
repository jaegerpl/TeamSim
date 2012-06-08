/**
 * 
 */
package de.haw.teamsim.semweb;

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

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
	
	public void buildGoalOntology(){
		OntModel m = ModelFactory.createOntologyModel();
		m.read("http://www.semanticweb.org/ontologies/2012/5/TeamSimGoalOntology");
		System.out.println(m.toString());
		FileManager fm = new FileManager();
		fm.readModel(m, "file:data/TeamSimGoalOntolofy.owl");
		sampleQuery(m);
	}
	
	public void sampleQuery(Model model){
		// list the statements in the Model
		StmtIterator iter = model.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // get next statement
		    Resource  subject   = stmt.getSubject();     // get the subject
		    Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object

		    System.out.print(subject.toString());
		    System.out.print(" " + predicate.toString() + " ");
		    if (object instanceof Resource) {
		       System.out.print(object.toString());
		    } else {
		        // object is a literal
		        System.out.print(" \"" + object.toString() + "\"");
		    }

		    System.out.println(" .");
		} 
	}

}
