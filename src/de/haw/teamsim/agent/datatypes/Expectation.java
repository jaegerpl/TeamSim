/**
 * 
 */
package de.haw.teamsim.agent.datatypes;

/**
 * @author pascal
 *
 */
public class Expectation {
	
	private Object fact = null;
	public enum ExpectationStatus {
		FAILED,
		FULFILLED,
		UNKNOW
	}
	
	private ExpectationStatus status;
	
	public Expectation(Object fact){
		this.fact = fact;
		status = ExpectationStatus.UNKNOW;
	}
	
	public ExpectationStatus getStatus(){
		return status;
	}
	
	public boolean hasFailed(){
		if(status== ExpectationStatus.FAILED){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFulfilled(){
		if(status== ExpectationStatus.FULFILLED){
			return true;
		} else {
			return false;
		}
	}

}
