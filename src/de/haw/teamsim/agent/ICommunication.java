/**
 * 
 */
package de.haw.teamsim.agent;

/**
 * @author pascal
 *
 */
public interface ICommunication {
	
	public void speakWith(String recipientRole, String topic, int priority, Object info);
	
	public void receiveInformation(Agent sender, String topic, int priority, Object info);

}
