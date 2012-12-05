/**
 * 
 */
package de.haw.teamsim.jade;

import jade.wrapper.AgentContainer;
import jade.core.MainContainer;
import jade.core.Runtime;
import jade.wrapper.*;
import jade.core.*;

/**
 * @author t-pascj
 *
 */
public class MyJadeSimulation {

	public static void main(String args[]) {
		try {
		   Runtime rt = Runtime.instance();
		   jade.core.Profile pMain = new jade.core.ProfileImpl();
		   AgentContainer mc = rt.createMainContainer(pMain);
		   Object ref = new Object();
		   Agent newagnt = mc.createNewAgent( "john",
		   MyJadeAgent.class.getName(), new Object[] {ref});
		   newagnt.start();
		} catch(Exception e) { e.printStackTrace(); }
	}
}
