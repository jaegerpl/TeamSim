package de.haw.teamsim.semweb.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

public class Task {

	// URI for vocabulary elements
    protected static final String uri = "http://burningbird.net/postcon/elements/1.0/";
    
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
    	   static final String   nresource = "resource";
	public static       Resource resource = null;
 
    // Instantiate the properties and the resource
    static {
    	// Instantiate the properties
    	performed_by = new PropertyImpl(uri, nperformed_by);
        use    		 = new PropertyImpl(uri, nuse);
        subtask 	 = new PropertyImpl(uri, nsubtask);
        resource 		= new ResourceImpl(uri+nresource);
    }
    
}
