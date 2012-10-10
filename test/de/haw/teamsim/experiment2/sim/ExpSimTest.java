package de.haw.teamsim.experiment2.sim;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import de.haw.teamsim.experiment2.ExpAction;
import de.haw.teamsim.experiment2.ExpAgent;
import de.haw.teamsim.experiment2.agent.CollabAgent;

public class ExpSimTest {

	static ExpSim sim;
	static List<ExpAction> orderedActions;
			
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sim = new ExpSim(2l);
		sim.start();
		orderedActions = sim.getOrderedAction();
	}



	@Test
	public void testSubmitForExecution() {
		ExpAction firstAction = orderedActions.get(0);
		ExpAction secondAction = orderedActions.get(1);
		ExpAgent agent = new CollabAgent("testagent");
		
		// submitting the wrong action should return false
		boolean result1 = sim.submitForExecution(secondAction, agent);
		
		// submitting first Action should return true
		boolean result2 = sim.submitForExecution(firstAction, agent);
		
		// submitting the action again, while it has not been marked as finished should return false;
		boolean result3 = sim.submitForExecution(firstAction, agent);
		
		// mark the correct submitted action as finished
		sim.markAsExecuted(firstAction);
		
		// submitting second action should now return true
		boolean result4 = sim.submitForExecution(secondAction, agent);
		
		assertTrue(result1 == false);
		assertTrue(result2 == true);
		assertTrue(result3 == false);
		assertTrue(result4 == true);
	}

}
