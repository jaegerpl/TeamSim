package de.haw.teamsim.semweb.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

import de.haw.teamsim.sim.TeamSim;

public class Subgoal {

	// URI for vocabulary elements
    protected static final String uri = TeamSim.getURI();
    
    // Return URI for vocabulary elements
    public static String getURI(  ){
        return uri;
    }    
    
    // Define the property labels and objects
    	   static final String   nnrequires = "requires";
    public static       Property requires = null;
	       static final String   nresource = "Subgoal";
	public static       Resource resource = null;
 
    // Instantiate the properties and the resource
    static {
    	// Instantiate the properties
    	requires = new PropertyImpl(uri, nnrequires);
    	resource 		= new ResourceImpl(uri+"#"+nresource);
    }
    
}
