package patientenrekrutierung.nlp.semantics;

import java.util.ArrayList;

/**
 * class for creating blacklist of terms which
 * should not be searched by ontoserver requests
 * @author Alexandra Banach
 *
 */
public class Blacklist {
	/**
	 * method for loading blacklist
	 *(terms which should not be searched by ontoserver requests)
	 * @return list of blacklist terms
	 */
	public ArrayList<String> getBlacklist(){
		ArrayList<String> blacklist = new ArrayList<String>();
		
		blacklist.add("not");
		blacklist.add("group");
		blacklist.add("study");
		blacklist.add("clinical trial");
		blacklist.add("trial");
		blacklist.add("nature");
		blacklist.add("risk");
		blacklist.add("consequence");
		blacklist.add("drug");
		blacklist.add("medication");
		blacklist.add("substance");
		blacklist.add("procedure");
		blacklist.add("evaluation");
		blacklist.add("research");
		blacklist.add("intervention");
		blacklist.add("surgery");
		blacklist.add("therapy");
		blacklist.add("treatment");
		blacklist.add("treat");
		blacklist.add("disease");
		blacklist.add("disorder");
		blacklist.add("diagnosis");
		blacklist.add("diagnose");
		blacklist.add("syndrome");
		blacklist.add("condition");
		blacklist.add("study");
		blacklist.add("followup");
		blacklist.add("follow-up");
		blacklist.add("illness");
		blacklist.add("condition");
		blacklist.add("children");
		blacklist.add("child");
		blacklist.add("adult");
		blacklist.add("adults");
		blacklist.add("analysis");
		blacklist.add("base");
		blacklist.add("eligibility");
		blacklist.add("relapse");
		blacklist.add("detection");
		blacklist.add("presence");
		blacklist.add("visit");
		blacklist.add("plan");
		blacklist.add("agent");
		blacklist.add("screening");
		blacklist.add("screen");
		blacklist.add("assessment");
		blacklist.add("active treatment");
		blacklist.add("adverse event");
		
		return blacklist;
	}
}
