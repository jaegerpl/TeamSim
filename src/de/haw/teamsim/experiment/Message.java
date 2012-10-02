/**
 * 
 */
package de.haw.teamsim.experiment;

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
	private ExpAgent receiver;
	private int actionID;
	private String content;
	private String conversationID = "";
	
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

	public ExpAgent getReceiver() {
		return receiver;
	}

	public Message(Type type){
		this.type = type;
	}
	
	public void setReceiver(ExpAgent agent){
		this.receiver = agent;
	}

}
