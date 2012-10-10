package de.haw.teamsim.experiment2.agent;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.haw.teamsim.experiment2.ExpAction;
import de.haw.teamsim.experiment2.ExpActionTemplate;

public class MentalModelTest {

	
	static MentalModel mm;
	static ExpAction a1;
	static int containID;
	static int notContainID;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		a1 = new ExpAction(0, 0, 0, 1);
		ExpAction a2 = new ExpAction(0, 0, 1, 3);
		ExpAction a3 = new ExpAction(0, 0, 2, 4);
		ExpAction a4 = new ExpAction(0, 0, 3, 5);
		containID = a4.getID();
		ExpAction a5 = new ExpAction(0, 0, 4, 6);
		ExpAction a6 = new ExpAction(0, 0, 5, 7);
		ExpAction a7 = new ExpAction(0, 0, 6, 0);
		ExpAction a8 = new ExpAction(0, 0, 6, 0);
		notContainID = a8.getID();
		mm = new MentalModel();
		mm.addAction(a7);
		mm.addAction(a6);
		mm.addAction(a5);
		mm.addAction(a4);
		mm.addAction(a3);
		mm.addAction(a2);
	}

	@Test
	public void testGetFirstAction() {
		ExpAction result1 = mm.getFirstAction();
		mm.addAction(a1);
		ExpAction result2 = mm.getFirstAction();
		
		assertTrue(!result1.equals(a1));
		assertTrue(result2.equals(a1));	
	}

	@Test
	public void testContains() {
		ExpActionTemplate tmp = new ExpActionTemplate(containID);
		boolean result1 = mm.contains(tmp);
		ExpActionTemplate tmp2 = new ExpActionTemplate(notContainID);
		boolean result2 = mm.contains(tmp2);
		
		assertTrue(result1);
		assertTrue(result2 == false);
		
	}

}
