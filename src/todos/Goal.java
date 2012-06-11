/**
 * 
 */
package todos;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.haw.teamsim.agent.Belief;


/**
 * @author pascal
 *
 */
public class Goal implements Observer{
	
	public enum GoalState {ACTIVE, SUSPENDED}
	private enum Action {SUSPEND, DROP, ACTIVATE, GENERATEPLAN, EXECUTEPLAN}
	public enum ConditionType {EMPTYPLANCONDITION, WITHPLANCONDITION} 
	
	private GoalState state = GoalState.SUSPENDED;
	private Plan plan = null;
	private List<Condition> emptyPlanConditions;
	private List<Condition> withPlanConditions;
	
	private class Condition {
		
		private Belief belief;
		private Action action;

		public  Condition(Belief b, Action a){
			belief = b;
			action = a;
		}		
	}
	
	public Goal(){
		emptyPlanConditions = new LinkedList<Condition>();
		withPlanConditions = new LinkedList<Condition>();
	}
	
	public void addCondition(Belief b, Action a, ConditionType t){
		if(t == ConditionType.EMPTYPLANCONDITION){
			emptyPlanConditions.add(new Condition(b,a));
		} else {
			withPlanConditions.add(new Condition(b,a));
		}
	}
	
	public GoalState getState(){
		return state;
	}
	
	/**
	 * Move the goal form one state to another: suspended <-> active -> drop <br>
	 * or generate or execute a plan for this goal.
	 * 
	 * @param action
	 * @return
	 */
	public boolean performAction(Action action){
		if(state == GoalState.SUSPENDED){
			if(action == Action.ACTIVATE){
				state = GoalState.ACTIVE;
				return true;
			} else if (action == Action.DROP){
				return false;
			}
		} else if(state == GoalState.ACTIVE){
			if(action == Action.SUSPEND){
				state = GoalState.SUSPENDED;
				return true;
			} else if(action == Action.DROP){
				// TODO: Drop method
				return true;
			} else if(action == Action.GENERATEPLAN){
				if(plan == null){
					//TODO Plan generation	
				}
				return true;
			} else if(action == Action.EXECUTEPLAN){
				if(plan != null){
					// TODO: execute plan
					return true;
				}
				return false;
			}
		}
		return false;
	}

	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof Belief){
			
		}
		// TODO Auto-generated method stub
		
	}

}
