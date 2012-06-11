/**
 * 
 */
package de.haw.teamsim.semweb;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
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
	
	private String uri = "http://www.semanticweb.org/ontologies/2012/5/TeamSimGoalOntology.owl";
	private Model model;
	
	public Property subtask; 			// all conditions are also in the main goal
	public Property responsible_for;	// some conditions are in the main goal, others are not
	public Property requires; 			// back direction of isSubGoal and extendsGoal
	public Property plays; 			// means that one or more conditions are conflictary
	public Property performed_by; 		// Points to the agent responsible for the goal
	public Property belongs_to;		// the area can be used to filter the visibility
	public Property consists_of;
	public Property has;				// The AgentRole that typically has the needed skills for a goal 
	public Property use;				//
	public Property used_by;		
	
	public SemanticGoalWeb(){
		model = ModelFactory.createDefaultModel();
		
		// all properties of the ontology
		subtask = model.getProperty(uri+"subtask");
		responsible_for = model.getProperty(uri+"responsible_for");
		requires = model.getProperty(uri+"requires");
		plays = model.getProperty(uri+"plays");
		performed_by = model.getProperty(uri+"performed_by");
		belongs_to = model.getProperty(uri+"belongs_to");
		consists_of = model.getProperty(uri+"consists_of");
		has = model.getProperty(uri+"has");
		use = model.getProperty(uri+"use");
		used_by = model.getProperty(uri+"subtask");	
	}
	
	/**
	 * Read RDF data from file at <code>location</code>
	 *
	 * @param location
	 */
	public void readRDFData(String location){
		
		FileManager fm = FileManager.get();
		fm.readModel(model, location, "RDF/XML");
		System.out.println("Triple count after inserts: " + (model.size()));
		
//		ResIterator parents = model.listSubjectsWithProperty(responsible_for);
//		while (parents.hasNext()) {
//			// ResIterator has a typed nextResource() method
//			Resource person = parents.nextResource();
//			// Print the URI of the resource
//			System.out.println(person.getURI());
//		}
	}
	
	/**
	 * Prints all statements that are in the <code>model</code>
	 * @param model
	 */
	public void printAllStatements(Model model){
		StmtIterator result = model.listStatements();
		while (result.hasNext()) {
			Statement st = result.next();
			System.out.println(st+"\n");
		}
	}

}
