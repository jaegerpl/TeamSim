/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package sim.app.wcss.tutorial04;
import sim.display.Console;
import sim.display.GUIState;
import sim.engine.SimState;

public class StudentsWithUI extends GUIState
    {
    public static void main(String[] args)
        {
        StudentsWithUI vid = new StudentsWithUI();
        Console c = new Console(vid);
        c.setVisible(true);
        }

    public StudentsWithUI() { super(new Students( System.currentTimeMillis())); }
    public StudentsWithUI(SimState state) { super(state); }

    public static String getName() { return "WCSS 2008 Tutorial"; }
    }
