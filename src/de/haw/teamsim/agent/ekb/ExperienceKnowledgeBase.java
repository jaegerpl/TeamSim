 /**
 * 
 */
package de.haw.teamsim.agent.ekb;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.haw.teamsim.agent.Belief;

/**
 * @author pascal
 *
 */
public class ExperienceKnowledgeBase implements Runnable {
	
	HashMap<Belief, List<Experience>> beliefMap;	// tracks the experiences a belief is part of
	HashMap<Integer, Experience> experienceMap;		// tracks the stored experiences
	
	
	/**
	 * Returns a HashMap of Experiences that at least contain one of the given beliefs.
	 * For each experience a list of matched beliefs is assigned as value.
	 * 
	 * @param beliefs
	 * @return
	 */
	public HashMap<Experience, List<Belief>> matchExperience(List<Belief> beliefs){
		
		HashMap<Integer, List<Belief>> foundBeliefs = new HashMap<Integer, List<Belief>>();  
		
		for(Belief b : beliefs){
			if(beliefMap.containsKey(b)){
				List<Experience> exps = beliefMap.get(b);
				for(Experience e : exps){
					if(foundBeliefs.containsKey(e.getID())){
						List<Belief> list = foundBeliefs.get(e.getID());
						list.add(b);
						foundBeliefs.put(e.getID(), list);
					} else {
						List<Belief> list = new LinkedList<Belief>();
						list.add(b);
						foundBeliefs.put(e.getID(), list);
					}					
				}
			}	
		}
		HashMap<Experience, List<Belief>> map = new HashMap<Experience, List<Belief>>();
		for(Integer i : foundBeliefs.keySet()){
			map.put(experienceMap.get(i), foundBeliefs.get(i));
		}
		return map;
	}
	
	public void addExperience(Experience exp){
		if(!experienceMap.containsKey(exp.getID())){
			for(Belief b : exp.getBeliefs()){
				if(beliefMap.containsKey(b)){
					List<Experience> list = beliefMap.get(b);
					list.add(exp);
					beliefMap.put(b, list);
				} else {
					List<Experience> list = new LinkedList<Experience>();
					list.add(exp);
					beliefMap.put(b, list);
				}	
			}
			experienceMap.put(exp.getID(), exp);
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
