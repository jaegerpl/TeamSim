package de.haw.teamsim.jade.behaviours;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class TeamMeetingBehaviour {

	private static final String STATE_A = "A";
	private static final String STATE_B = "B";
	private static final String STATE_C = "C";
	private static final String STATE_D = "D";
	private static final String STATE_E = "E";
	private static final String STATE_F = "F";
	
	public static FSMBehaviour createBehaviour(Agent a){
		
		
		
		FSMBehaviour fsm = new FSMBehaviour(a) {
			public int onEnd() {
				System.out.println("TeamMeetingBehaviour behaviour completed.");
				myAgent.doDelete();
				return super.onEnd();
			}
		};
		
		// Register state A (first state)
		fsm.registerFirstState(new requestMeetingMembers(), STATE_A);
		
		// Register state B
		fsm.registerState(new sendMeetingTopic(), STATE_B);
		
		// Register state C
		fsm.registerState(new RandomGenerator(3), STATE_C);
		
		// Register state D
		fsm.registerState(new NamePrinter(), STATE_D);
		
		// Register state E
		fsm.registerState(new RandomGenerator(4), STATE_E);
		
		// Register state F (final state)
		fsm.registerLastState(new NamePrinter(), STATE_F);

		// Register the transitions
		fsm.registerDefaultTransition(STATE_A, STATE_B);
		fsm.registerDefaultTransition(STATE_B, STATE_C);
		fsm.registerTransition(STATE_C, STATE_C, 0);
		fsm.registerTransition(STATE_C, STATE_D, 1);
		fsm.registerTransition(STATE_C, STATE_A, 2);
		fsm.registerDefaultTransition(STATE_D, STATE_E);
		fsm.registerTransition(STATE_E, STATE_F, 3);
		fsm.registerDefaultTransition(STATE_E, STATE_B);
		
		return fsm;
	}
	
	private class requestMeetingMembers extends OneShotBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
		}		
	}
	
	private class sendMeetingTopic extends OneShotBehaviour {

		@Override
		public void action() {
			// TODO Auto-generated method stub			
		}		
	}
}
