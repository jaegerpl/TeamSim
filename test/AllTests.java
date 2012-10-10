import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import de.haw.teamsim.experiment2.agent.MentalModelTest;
import de.haw.teamsim.experiment2.sim.ExpSimTest;
 
/**
 * JUnit Suite Test
 * @author mkyong
 *
 */
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExpSimTest.class,
        MentalModelTest.class
})
public class AllTests {
}