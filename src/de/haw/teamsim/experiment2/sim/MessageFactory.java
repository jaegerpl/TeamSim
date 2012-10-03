package de.haw.teamsim.experiment2.sim;

import de.haw.teamsim.experiment2.sim.Message.Type;


public class MessageFactory {
	
	private String sender;
	
	public MessageFactory(String sender){
		this.sender = sender;
	}
	
	public Message informMessage(String receiver, String content){
		Message msg = new Message(Type.INFORM);
		msg.setSender(sender);
		msg.setReceiver(receiver);
		msg.setContent(content);
		return msg;
	}
	
	public Message requestMessage(String receiver, String content){
		Message msg = new Message(Type.INFORM);
		msg.setSender(sender);
		msg.setReceiver(receiver);
		msg.setContent(content);
		return msg;
	}
}
