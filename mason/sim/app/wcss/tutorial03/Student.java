/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package sim.app.wcss.tutorial03;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Student implements Steppable
    {
    public void step(SimState state)
        {
        Students students = (Students) state;
        Continuous2D yard = students.yard;

        Double2D me = students.yard.getObjectLocation(this);

        MutableDouble2D sumForces = new MutableDouble2D();

        // add in a vector to the "teacher" -- the center of the yard, so we don't go too far away
        sumForces.addIn(new Double2D((yard.width * 0.5 - me.x) * students.forceToSchoolMultiplier, 
                (yard.height * 0.5 - me.y) * students.forceToSchoolMultiplier));
        
        // add a bit of randomness
        sumForces.addIn(new Double2D(students.randomMultiplier * (students.random.nextDouble() * 1.0 - 0.5), 
                students.randomMultiplier * (students.random.nextDouble() * 1.0 - 0.5)));

        sumForces.addIn(me);

        students.yard.setObjectLocation(this, new Double2D(sumForces));
        }
    } 
    
