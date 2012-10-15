package de.haw.teamsim.experiment2.view;

import sim.display.GUIState;
import sim.portrayal.FieldPortrayal2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.SimpleInspector;
import sim.portrayal.inspector.TabbedInspector;
import de.haw.teamsim.experiment2.agent.CollabAgent;

public class AgentStatePortrayal extends FieldPortrayal2D {
	
	public Inspector getInspector(LocationWrapper wrapper, GUIState state){
		if(wrapper == null){
			return null;
		}
		
		TabbedInspector inspector = new TabbedInspector();
		Object o = wrapper.getObject();
		if(o instanceof CollabAgent){
			CollabAgent agent = (CollabAgent)o;
			 inspector.addInspector(new SimpleInspector(agent, state), "Agens");
		}
		
		return null;		
	}

}
