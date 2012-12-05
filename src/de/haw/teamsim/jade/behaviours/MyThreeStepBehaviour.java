/**
 * 
 */
package de.haw.teamsim.jade.behaviours;

import jade.core.behaviours.Behaviour;

/**
 * @author t-pascj
 *
 */
public class MyThreeStepBehaviour extends Behaviour {
private int step = 0;
public void action() {
switch (step) {
case 0:
System.out.println("State 1");
step++;
break;
case 1:
	System.out.println("State 2");
step++;
break;
case 2:
	System.out.println("State 3");
step++;
break;
}
}
public boolean done() {
return step == 3;
}
}
