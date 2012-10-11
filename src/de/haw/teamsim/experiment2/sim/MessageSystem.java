package de.haw.teamsim.experiment2.sim;

import java.util.LinkedList;
import java.util.List;

import de.haw.teamsim.experiment2.ExpAgent;

public class MessageSystem implements Runnable{
	
	private List<Message> messageBus;
	private List<ExpAgent> agents = new LinkedList<ExpAgent>();
	
	public MessageSystem(){
		messageBus = new LinkedList<Message>();
	}

	@Override
	public void run() {
		while(true){
			//System.out.println("MessageSystem is running");
			if(!messageBus.isEmpty()){
				Message msg = messageBus.remove(0);
				String r = msg.getReceiver();
				if(r.equalsIgnoreCase(ExpSim.Team)){
					for(ExpAgent agent : agents){
						if(!agent.getName().equalsIgnoreCase(msg.getSender())){
							agent.receiveMsg(msg);
						}						
					}
				} else {
					for(ExpAgent agent : agents){
						if(agent.getName() == r){
							agent.receiveMsg(msg);
						}						
					}
				}
				System.out.println("MessageSystem has send: "+msg);
			}
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void sendMessage(Message msg){
		messageBus.add(msg);
//		System.out.println("MessageSystem received: "+msg);
	}

	public void addAgents(List<ExpAgent> a){
		this.agents = a;
//		System.out.println("MessageSystem has agents: "+agents);
	}
}
