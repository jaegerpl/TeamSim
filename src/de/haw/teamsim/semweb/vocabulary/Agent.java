package de.haw.teamsim.semweb.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

public class Agent {

	// URI for vocabulary elements
    protected static final String uri = "http://burningbird.net/postcon/elements/1.0/";
    
    // Return URI for vocabulary elements
    public static String getURI(  )
    {
        return uri;
    }    
    
    // Define the property labels and objects
           static final String   nplays = "plays";
    public static       Property plays = null;       
           static final String   nbelongs_to = "belongs_to";
    public static       Property belongs_to = null;         
    	   static final String   nresource = "resource";
    public static       Resource resource = null;  
 
    // Instantiate the properties and the resource
    static {
            // Instantiate the properties
    		plays			= new PropertyImpl(uri, nplays);
        	belongs_to    	= new PropertyImpl(uri, nbelongs_to);
        	resource 		= new ResourceImpl(uri+nresource);
    }
    
}

