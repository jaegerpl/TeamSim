package de.haw.teamsim.experiment2.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.haw.teamsim.experiment2.ExpAction;

public class MentalModel {
	
	private Map<Integer, ExpAction> actions;
	private int firstActionID = -1;
	
	public MentalModel(){
		actions = new HashMap<Integer, ExpAction>();
	}
	
	public void addAction(ExpAction a){
		actions.put(a.getID(), a);
		if(a.getPredecessorID()== 0){
			firstActionID = a.getID();	
		}
	}
	
	public List<ExpAction> getActions(){
		List<ExpAction> list = new LinkedList<ExpAction>();
		if(firstActionID == -1){
			list = new ArrayList<ExpAction>(actions.values());
		} else {
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
				tmp.add(list.size()-1, null);
				Collections.copy(tmp, list);
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

	public ExpAction getFirstAction() {		
		return getActions().get(0);
	}

	public boolean contains(ExpAction actiontmp) {
		return actions.containsValue(actiontmp);
	}

	public void remove(int actionID) {
		actions.remove(actionID);
	}

}
