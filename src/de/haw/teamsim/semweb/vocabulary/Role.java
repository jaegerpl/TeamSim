package de.haw.teamsim.semweb.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

import de.haw.teamsim.sim.TeamSim;

public class Role {

	// URI for vocabulary elements
    protected static final String uri = TeamSim.getURI();
    
    // Return URI for vocabulary elements
    public static String getURI(  ){
        return uri;
    }    
    
    // Define the property labels and objects
           static final String   nresponsible_for = "responsible_for";
    public static       Property responsible_for = null;       
           static final String   nplays = "plays";
    public static       Property plays = null;   
    	   static final String   nrequires = "requires";
    public static       Property requires = null;
	   static final String   nworks_in = "works_in";
	public static       Property works_in = null;
	   	   static final String   nresource = "Role";
	public static       Resource resource = null;
	
	// Instantiate the properties and the resource
    static {
            // Instantiate the properties
    		responsible_for = new PropertyImpl(uri, nresponsible_for);
        	plays    = new PropertyImpl(uri, nplays);
        	requires = new PropertyImpl(uri, nrequires);
        	works_in = new PropertyImpl(uri, nworks_in);
        	resource 		= new ResourceImpl(uri+"#"+nresource);
    }
    
}
