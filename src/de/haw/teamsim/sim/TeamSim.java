package de.haw.teamsim.sim;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import de.haw.teamsim.agent.Agent;
import de.haw.teamsim.agent.Agent.AgentRole;

public class TeamSim extends SimState {

	private static final long serialVersionUID = 1L;
	public Continuous2D world = new Continuous2D(1.0, 100, 100);
	public int numPeople = 5;
	public double forceToSchoolMultiplier = 0.01;
	public double randomMultiplier = 0.1;
	
	private Team team;

	public static Logger log = Logger.getLogger(TeamSim.class);

	public TeamSim(long seed) {
		super(seed);
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		
		team = new Team();
	}

	public void start() {
		super.start();
		world.clear();

		Agent agent1;
		Agent agent2;
		Agent agent3;

		agent1 = new Agent("Paul", AgentRole.Manager, team);
		agent2 = new Agent("Lucy", AgentRole.FireDepartement, team);
		agent3 = new Agent("Charlie", AgentRole.PolicyDepartement, team);

//		world.setObjectLocation(agent, new Double2D(source.getX(), source.getY()));
		schedule.scheduleRepeating(agent1);
		schedule.scheduleRepeating(agent2);
		schedule.scheduleRepeating(agent3);

	}

	public static void main(String[] args) {
		doLoop(TeamSim.class, args);
		System.exit(0);
	}
}