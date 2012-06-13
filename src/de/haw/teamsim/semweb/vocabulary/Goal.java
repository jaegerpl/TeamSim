package de.haw.teamsim.semweb.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

import de.haw.teamsim.sim.TeamSim;

public class Goal {

	// URI for vocabulary elements
    protected static final String uri = TeamSim.getURI();
    
    // Return URI for vocabulary elements
    public static String getURI(  ){
        return uri;
    }    
    
    // Define the property labels and objects
    	   static final String   nconsists_of = "consists_of";
    public static       Property consists_of = null;
    	   static final String   nresource = "Goal";
    public static       Resource resource = null;
 
    // Instantiate the properties and the resource
    static {
        // Instantiate the properties
    	consists_of = new PropertyImpl(uri, nconsists_of);
    	resource 		= new ResourceImpl(uri+"#"+nresource);
    }
    
}
