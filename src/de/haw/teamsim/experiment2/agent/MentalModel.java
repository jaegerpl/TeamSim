package de.haw.teamsim.experiment2.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.haw.teamsim.experiment2.ExpAction;
import de.haw.teamsim.experiment2.ExpActionTemplate;

public class MentalModel {
	
	private Map<Integer, ExpAction> actions;
	private int firstActionID = -1;
	
	public MentalModel(){
		actions = new HashMap<Integer, ExpAction>();
	}
	
	public void addAction(ExpAction a){
		actions.put(a.getID(), a);
		if(!(a instanceof ExpActionTemplate) && a.getPredecessorID()== 0){
			firstActionID = a.getID();	
		}
	}
	
	/**
	 * Returns the actions in sequence as far as it is possible to sort them
	 * @return
	 */
	public List<ExpAction> getActions(){
		List<ExpAction> list = new LinkedList<ExpAction>();
		
		if(firstActionID == -1){
			// if not owner of the first action, simply return the set of actions as list
			list = new ArrayList<ExpAction>(actions.values());
		} else {
			// start with the first ordered actions and then simply add the rest of the set
			ExpAction a = actions.get(firstActionID);
			list.add(a);
			while(a.getSuccessorID() != 0){
				a = actions.get(a.getSuccessorID());
				if(a == null){
					break;
				}
				list.add(a);
			}
			if(list.size() < actions.size()){
				List<ExpAction> tmp = new LinkedList<ExpAction>();
				for(ExpAction c : actions.values()){
					tmp.add(c);
				}
				for(ExpAction b : list){
					tmp.remove(list.indexOf(b));
				}
				for(ExpAction c : tmp){
					list.add(c);
				}
			}
		}
		return list;
	}

	/**
	 * Returns the first action in sequence, if it is known by the model. Otherwise returns the first action of the model
	 * 
	 * @return
	 */
	public ExpAction getFirstAction() {	
		if(firstActionID != -1){
			return actions.get(firstActionID);
		}
		return getActions().get(0);
	}

	public boolean contains(ExpAction actiontmp) {
		return actions.containsValue(actiontmp);
	}

	public void remove(int actionID) {
		actions.remove(actionID);
	}

}
