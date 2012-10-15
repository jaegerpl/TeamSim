package de.haw.teamsim.experiment2.view;

import java.awt.Color;
import java.util.Iterator;

import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.display.RateAdjuster;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.SimpleInspector;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.inspector.TabbedInspector;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.util.Bag;
import de.haw.teamsim.experiment2.agent.CollabAgent;
import de.haw.teamsim.experiment2.sim.ExpSim;

public class ExpSimUI extends GUIState {
	
	private static ExpSim sim = new ExpSim(System.currentTimeMillis());
	public Display2D display;
    public JFrame displayFrame;
    SparseGridPortrayal2D agentsPortrayal = new SparseGridPortrayal2D(); 
    
    private Bag b1 = new Bag();
    private Bag b2 = new Bag();
    private TabbedInspector tabInspector = new TabbedInspector(true);
    private Controller c;
	
	public static void main(String[] args){

		new ExpSimUI().createController();
//		ExpSimUI vid = new ExpSimUI();
//		Console c = new Console(vid);
//		c.setVisible(true);
	}
	
	public void init(Controller c){
		super.init(c);
		this.c = c;
		
//		display = new Display2D(20,20,this);
//        displayFrame = display.createFrame();
//        c.registerFrame(displayFrame);
//        displayFrame.setVisible(true);
//        display.setBackdrop(Color.white);
//        display.attach(agentsPortrayal, "Agents");

		tabInspector.setUpdatingAllInspectors(true);
		
        b1.add(tabInspector);
        b2.add("Agents");  
        c.setInspectors(b1, b2);
		c.registerInspector(tabInspector, null);
	}
	
	public ExpSimUI(){
		super(sim); 
	}
	
	public ExpSimUI(SimState state){
		super(state); 
	}
	
	public void start(){		
		super.start();
		scheduleRepeatingImmediatelyAfter(new RateAdjuster(2.0));		
//		setupPortrayals();
		setupInspectors();
	}
	
	private void setupInspectors() {
		Bag agents = sim.getAgents().getAllObjects();
		Iterator<CollabAgent> it = agents.iterator();
		while(it.hasNext()){
			CollabAgent agent = it.next();
			SimpleInspector insp = new SimpleInspector(agent, this);
			tabInspector.addInspector(insp, agent.getName());
		}
		b1.remove(tabInspector);
		b1.add(tabInspector);
		c.setInspectors(b1, b2);
		c.registerInspector(tabInspector, null);
		c.refresh();
		tabInspector.repaint();
		
	}

	private void setupPortrayals() {
		
		agentsPortrayal.setField(sim.getAgents());
		agentsPortrayal.setPortrayalForClass(CollabAgent.class, 
											 new OvalPortrayal2D(Color.black){
			public Inspector getInspector(LocationWrapper wrapper, GUIState state){
				return new AgentPropertyInspector(super.getInspector(wrapper,  state), 
												  wrapper, state);
			}
		});
		
		display.reset();
		display.repaint();
	}

	public static String getName(){
		return "Team Experiment";
	}
	
	public static String getInfo(){
		return "This is an experiment, demonstrating how agents can communicate"+
				" to coordinate their actions in sequence.";
	}
	
	public Object getSimulationInspectedObject(){ return state;}
	
	public Inspector getInspector(){
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}
	
	public void quit(){
	    super.quit();
	    
	    if (displayFrame!=null) displayFrame.dispose();
	    displayFrame = null;  // let gc
	    display = null;       // let gc
    }
}