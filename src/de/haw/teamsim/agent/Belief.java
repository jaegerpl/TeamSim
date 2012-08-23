package de.haw.teamsim.agent;

import java.util.Observable;

/**
 * A believe is a guess about a property of the world.
 * 
 * @author pascal
 *
 */
public class Belief extends Observable{
	
	private final String name;
	private Object fact;
	private float certainty;	// how certain is the agent about the fact
	
	public Belief(String name, Object fact){
		this.name = name;
		this.fact = fact;
		this.certainty = 1;
	}
	
	/**
	 * Creates a new Belief with a name, the fact, i.e. the belief and a certainty of the agent about the fact
	 * @param name
	 * @param fact
	 * @param certainty - a value between 0 and 1
	 */
	public Belief(String name, Object fact, float certainty){
		this(name, fact);
		this.certainty = certainty;
	}
	
	/**
	 * Updates the fact stored in the believe
	 * @param fact
	 * @return the old fact
	 */
	public Object updateFact(Object fact){
		Object f = fact;
		this.fact = fact;
		notifyObservers(name);
		return f;
	}
	
	public float updateCertainty(float certainty){
		float old = certainty;
		this.certainty = certainty;
		notifyObservers(name);
		return old;
	}
	
	public Object getFact(){
		return fact;
	}
	
	public float getCertainty(){
		return certainty;
	}
	
	public String getName(){
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Two Beliefs are equals if they have the same name and their facts are of the same class
	 */
	public boolean equals(Object obj){
		Belief b;
		if( obj instanceof Belief){
			b = (Belief)obj;
			if(name.equals(b.name)){
				if(b.fact.getClass() == this.fact.getClass()){
					return true;
				}
			}
		}
		return false;
	}
	
	public String toString(){
		return "Belief("+name+", "+fact+")";
	}

}
