package de.haw.teamsim.experiment2.sim;

import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.display.RateAdjuster;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.SimpleInspector;
import sim.portrayal.inspector.TabbedInspector;
import sim.util.Bag;
import de.haw.teamsim.experiment2.ExpAgent;
import de.haw.teamsim.experiment2.agent.CollabAgent;

public class ExpSimUI extends GUIState {
	
	private static ExpSim sim = new ExpSim(System.currentTimeMillis());
	private TabbedInspector tabInsp;
	public Display2D display;
    public JFrame displayFrame;
    private boolean first = true;
    
    private Bag b1 = new Bag();
    private Bag b2 = new Bag();
    private Controller c;
	
	public static void main(String[] args){

		ExpSimUI vid = new ExpSimUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}
	
	public void init(Controller c){
		this.c = c;
		
		tabInsp = new TabbedInspector(true);
		tabInsp.setUpdatingAllInspectors(true);
		
        b1.add(tabInsp);
        b2.add("Agents");  
        c.setInspectors(b1, b2);
		// make the displays
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
		if(first){
			first = false;
			setupInspectors();
		}
	}
	
	private void setupInspectors() {
		System.out.println("Second: "+sim.getAgents());
		
		for(ExpAgent agent : sim.getAgents()){
			Inspector agent1Insp = new SimpleInspector((CollabAgent)agent, this);
			tabInsp.addInspector(agent1Insp, agent.getName());
		}
		c.setInspectors(b1, b2);
		display.reset();
		display.repaint();
	}

	public static String getName(){
		return "Team Experiment";
	}
	
	public static String getInfo(){
		return "This is an experiment, demonstrating how agents can communicate to coordinate their actions in sequence.";
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