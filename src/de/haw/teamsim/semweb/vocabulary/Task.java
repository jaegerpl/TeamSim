package de.haw.teamsim.semweb.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

import de.haw.teamsim.sim.TeamSim;

public class Task {

	// URI for vocabulary elements
    protected static final String uri = TeamSim.getURI();
    
    // Return URI for vocabulary elements
    public static String getURI(  ){
        return uri;
    }    
    
    // Define the property labels and objects
           static final String   nperformed_by = "performed_by";
    public static       Property performed_by = null;       
           static final String   nuse = "use";
    public static       Property use = null;   
    	   static final String   nsubtask = "subtask";
    public static       Property subtask = null;
    	   static final String   nresource = "Task";
	public static       Resource resource = null;
 
    // Instantiate the properties and the resource
    static {
    	// Instantiate the properties
    	performed_by = new PropertyImpl(uri, nperformed_by);
        use    		 = new PropertyImpl(uri, nuse);
        subtask 	 = new PropertyImpl(uri, nsubtask);
        resource 		= new ResourceImpl(uri+"#"+nresource);
    }
    
}
