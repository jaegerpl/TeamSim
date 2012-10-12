package de.haw.teamsim.experiment2.sim;

import javax.swing.JFrame;

import sim.display.*;
import sim.display3d.Display3D;
import sim.engine.*;
import sim.portrayal.Inspector;
import sim.portrayal.SimpleInspector;
import sim.portrayal.inspector.TabbedInspector;
import de.haw.teamsim.experiment2.*;
import de.haw.teamsim.experiment2.agent.*;;

public class ExpSimUI extends GUIState {
	
	private static ExpSim sim = new ExpSim(System.currentTimeMillis());
	private Display2D display;
	private JFrame displayFrame;
	
	public static void main(String[] args){

		ExpSimUI vid = new ExpSimUI();
		Console c = new Console(vid);
		
		
		
		c.setVisible(true);
	}
	
	public void init(Controller c){
		// make the displayer
		ExpAgent agent = new CollabAgent("Name");
		Inspector agent1Insp = new SimpleInspector(sim.getAgents(), this);
		display = new Display2D(600, 600,this);           
        display.attach(agent1Insp , agent1Insp.getName() );

        displayFrame = display.createFrame();
        displayFrame.setTitle("Embryo");
        c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
        displayFrame.setVisible(true);
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
}