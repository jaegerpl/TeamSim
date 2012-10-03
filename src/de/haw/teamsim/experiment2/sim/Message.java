/**
 * 
 */
package de.haw.teamsim.experiment2.sim;

/**
 * An ACL like Message class
 * @author pascal
 *
 */
public class Message {
	
	public final static String executed = "executed";
	public final static String ownedBy = "owned by sender";
	public final static String whoHas = "who has";
	public final static String plzExecute= "please execute";
	
	public static enum Type {INFORM, REQUEST}

	
	private Type type ;
	private String receiver;
	private int actionID;
	private String content;
	private String conversationID = "";
	private String sender;
	
	public int getActionID() {
		return actionID;
	}

	public void setActionID(int actionID) {
		this.actionID = actionID;
	}

	public String getConversationID() {
		return conversationID;
	}

	public void setConversationID(String conversationID) {
		this.conversationID = conversationID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Type getType() {
		return type;
	}

	public String getReceiver() {
		return receiver;
	}

	public Message(Type type){
		this.type = type;
	}
	
	public void setReceiver(String agent){
		this.receiver = agent;
	}
	
	public String getSender(){
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String toString(){
		return "[Sender: "+sender+", Receiver: "+receiver+", Type: "+type+", Content: "+content+", ActionID: "+actionID+"]";
	}
	
}
