package de.haw.teamsim.experiment2.view;

import java.awt.Frame;

import de.haw.teamsim.experiment2.agent.CollabAgent;
import de.haw.teamsim.experiment2.sim.ExpSim;

import sim.app.tutorial4.BigParticle;
import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.inspector.PropertyInspector;
import sim.util.Properties;

public class AgentPropertyInspector extends Inspector {
	
	public static String name() { return "Stream"; }
    public static Class[] types() { return null; } // accepts all types
    private Inspector originalInspector;
	
//	public AgentPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation){
//		super(properties, index, null, simulation);
//		setValidInspector(true);
//	}
    
    public AgentPropertyInspector(Inspector originalInspector, 
    								LocationWrapper wrapper, 
    								GUIState guiState){
    	this.originalInspector = originalInspector;
        
        // get info out of the wrapper
        SparseGridPortrayal2D gridportrayal = (SparseGridPortrayal2D) wrapper.getFieldPortrayal();
        // these are final so that we can use them in the anonymous inner class below...
        final SparseGrid2D grid = (SparseGrid2D)(gridportrayal.getField());
        final CollabAgent agent = (CollabAgent) wrapper.getObject();
        final SimState state = guiState.state;
        final Controller console = guiState.controller; 
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void updateInspector(){
		originalInspector.updateInspector();
    }

}
