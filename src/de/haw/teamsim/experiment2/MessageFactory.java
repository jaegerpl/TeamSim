package de.haw.teamsim.experiment2;

import de.haw.teamsim.experiment2.Message.Type;

public class MessageFactory {
	
	private ExpAgent sender;
	
	public MessageFactory(ExpAgent sender){
		this.sender = sender;
	}
	
	public Message informMessage(ExpAgent receiver, String content){
		Message msg = new Message(Type.INFORM);
		msg.setSender(sender);
		msg.setReceiver(receiver);
		msg.setContent(content);
		return msg;
	}
	
	public Message requestMessage(ExpAgent receiver, String content){
		Message msg = new Message(Type.INFORM);
		msg.setSender(sender);
		msg.setReceiver(receiver);
		msg.setContent(content);
		return msg;
	}
}
