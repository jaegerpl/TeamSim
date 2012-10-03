package de.haw.teamsim.experiment2.sim;

import sim.display.*;
import sim.engine.*;

public class ExpSimUI extends GUIState {

	public static void main(String[] args){

		ExpSimUI vid = new ExpSimUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}
	
	public ExpSimUI(){
		super(new ExpSim(System.currentTimeMillis())); 
	}
	
	public ExpSimUI(SimState state){
		super(state); 
	}
	
	public static String getName(){
		return "Team Experiment";
	}
}