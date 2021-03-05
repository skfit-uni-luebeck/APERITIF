package patientenrekrutierung.nlp.semantics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import patientenrekrutierung.datastructure.querybuilding.Conjunction;
import patientenrekrutierung.datastructure.querybuilding.ConjunctionType;

/**
 * class for extracting conjunctions from eligiblity criteria
 * @author Alexandra Banach
 *
 */
public class ConjunctionGenerator {
	
	/**
	 * method for extracting conjunctions (AND, OR, EXCEPT) from eligibility criteria
	 * @param criterion eligiblity criterion from which conjunctions should be extracted
	 * @return list of extracted conjunctions
	 */
	public ArrayList<Conjunction> extractConjunctions(String criterion){
		
		// initialize
		ArrayList<Conjunction> conjunctions = new ArrayList<Conjunction>();
		// replace and/ or by logical or
		criterion = criterion.replaceAll("and/or", "or");
		criterion = criterion.replaceAll("and / or", "or");
		criterion = criterion.replaceAll("and/ or", "or");
		
		
		// extract and
		Pattern regexAnd = Pattern.compile("and");
		Matcher matcherAnd = regexAnd.matcher(criterion);
		while(matcherAnd.find()){
			conjunctions.add(new Conjunction(ConjunctionType.AND, matcherAnd.start()));
		}
		
		// extract or
		Pattern regexOr = Pattern.compile("or");
		Matcher matcherOr = regexOr.matcher(criterion);
		while(matcherOr.find()){
			conjunctions.add(new Conjunction(ConjunctionType.OR, matcherOr.start()));
		}
		
		// extract except
		Pattern regexExcept = Pattern.compile("except");
		Matcher matcherExcept = regexExcept.matcher(criterion);
		while(matcherExcept.find()){
			conjunctions.add(new Conjunction(ConjunctionType.EXCEPT, matcherExcept.start()));
		}
		
		// sort list by position of conjunction ascending
		Collections.sort(conjunctions, Comparator.comparing(Conjunction::getPosition));
			
		return conjunctions;
	}
}
