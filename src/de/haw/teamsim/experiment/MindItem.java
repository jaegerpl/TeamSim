package de.haw.teamsim.experiment;

/**
 * Holds an action and the owner of the action
 * 
 * @author pascal
 *
 */
public class MindItem {
	
	int actionID;
	ExpAgent owner;
	
	public MindItem(ExpAction action, ExpAgent owner){
		this.owner = owner;
		actionID = action.getID();
	}

}
