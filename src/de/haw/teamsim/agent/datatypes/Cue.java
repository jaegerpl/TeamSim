/**
 * 
 */
package de.haw.teamsim.agent.datatypes;

/**
 * A cue is a fact about a world state that, if becoming true, 
 * immediately alerts the agent monitoring the cue. 
 * It triggers an action associated with the cue. 
 * This trigger is part of the experience.<br>
 * <br>
 * <b>Conceptional Thoughts</b><br>
 * For now I think, the cue is stored in the experience, and when the experience is
 * activated, the cue is handed over the the cue monitoring component, which registers
 * itself as an observer to the belief base, waiting for a belief to turn into a certain state.
 * It then triggers the action associated with the cue.
 * 
 * @author pascal
 *
 */
public class Cue {

}
