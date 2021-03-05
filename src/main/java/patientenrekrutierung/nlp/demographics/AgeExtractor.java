package patientenrekrutierung.nlp.demographics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.tokenize.SimpleTokenizer;
import patientenrekrutierung.datastructure.demographics.Age;
import patientenrekrutierung.datastructure.demographics.AgeUnit;
import patientenrekrutierung.datastructure.demographics.AgeUnitsEnum;
import patientenrekrutierung.datastructure.demographics.AgeValue;
import patientenrekrutierung.datastructure.demographics.MinMaxAge;

/**
 * class for extracting information about
 * minimum and maximum age of patients from
 * free-text eligibility criteria
 * @author Alexandra Banach
 *
 */
public class AgeExtractor {
	/**
	 * extracting information about minimum and
	 * maximum age of patients from free-text
	 * eligibility criteria
	 * @param criterion eligibility criterion
	 * @return extracted information about minimum and maximum age of patients
	 */
	public MinMaxAge extractAge(String criterion){
		

		// extract age values
		AgeExtractor ageExtractor = new AgeExtractor();
		ArrayList<AgeValue> ageValues = ageExtractor.extractAgeValues(criterion);

		// extract age units
		ArrayList<AgeUnit> ageUnits = ageExtractor.extractAgeUnit(criterion);

		// matching values with units, make pairs
		ArrayList<Age> ageUnitPairs = new ArrayList<Age>();
		for (AgeValue value : ageValues) {
			for (AgeUnit unit : ageUnits) {
				if (unit.getPosition() > value.getPosition()) {
					ageUnitPairs.add(new Age(value, unit));
					break;

				}
			}
		}

		// extract minimum and maximum in age value pairs
		MinMaxAge minMaxAge = ageExtractor.extractMinMaxAge(ageUnitPairs, criterion);

		return minMaxAge;
	}
	
	/**
	 * method for deciding which extracted age-unit-pair
	 * is minimum and maximum age
	 * @param ageUnitPairs pairs of age value and time unit
	 * @param criterion eligibility criterion
	 * @return extracted information about minimum and maximum age of patients
	 */
	private MinMaxAge extractMinMaxAge(ArrayList<Age> ageUnitPairs, String criterion){
		AgeExtractor ageExtractor = new AgeExtractor();
		MinMaxAge minMaxAge = new MinMaxAge(null, null);
		
		// only one number/ unit found
		if(ageUnitPairs.size() == 1){
			// check if maximum or minimum
			ArrayList<Integer> minimums = ageExtractor.extractMinimumPositions(criterion);
			ArrayList<Integer> maximums = ageExtractor.extractMaximumPositions(criterion);
			
			// case maximum found
			if(minimums.isEmpty() && !maximums.isEmpty()){
				// set maximum
				minMaxAge.setMaxAge(ageUnitPairs.get(0));
			}else{
				// else: set minimum 
				minMaxAge.setMinAge(ageUnitPairs.get(0));
			}
			// exactly two numbers/ units found
		}else if(ageUnitPairs.size() == 2){
			ArrayList<Integer> minimums = ageExtractor.extractMinimumPositions(criterion);
			ArrayList<Integer> maximums = ageExtractor.extractMaximumPositions(criterion);
			
			// only one mimimum and maximum is found
			if(!minimums.isEmpty() && !maximums.isEmpty()){
				// if maximum was first found
				if(minimums.get(0) > maximums.get(0)){
					// first entry is maximum
					minMaxAge.setMaxAge(ageUnitPairs.get(0));
					minMaxAge.setMinAge(ageUnitPairs.get(1));
				}else{
					// first one is minimum, second one maximum
					minMaxAge.setMaxAge(ageUnitPairs.get(1));
					minMaxAge.setMinAge(ageUnitPairs.get(0));
				}
			// two minimums are found	
			}else if(!minimums.isEmpty() && maximums.isEmpty()){
				// take first one
				minMaxAge.setMinAge(ageUnitPairs.get(0));
			// two maximums are found
			}else if(!maximums.isEmpty() && minimums.isEmpty()){
				// take first one
				minMaxAge.setMaxAge(ageUnitPairs.get(0));
			}else{
				// take both e.g. 61 to 80 years
				minMaxAge.setMinAge(ageUnitPairs.get(0));
				minMaxAge.setMaxAge(ageUnitPairs.get(1));
			}
			
		// more than two pairs found: 
		}else if(ageUnitPairs.size() > 2){
			ArrayList<Integer> minimums = ageExtractor.extractMinimumPositions(criterion);
			ArrayList<Integer> maximums = ageExtractor.extractMaximumPositions(criterion);
			if(!minimums.isEmpty() && maximums.isEmpty()){
				// take first one
				minMaxAge.setMinAge(ageUnitPairs.get(0));
			// two maximums are found
			}else if(!maximums.isEmpty() && minimums.isEmpty()){
				// take first one
				minMaxAge.setMaxAge(ageUnitPairs.get(0));
			}else{
				// take both e.g. 61 to 80 years
				minMaxAge.setMinAge(ageUnitPairs.get(0));
				minMaxAge.setMaxAge(ageUnitPairs.get(1));
			}
		}else{
			// if empty return empty object
		}
		
		
		return minMaxAge;
	}
	
	/**
	 * method for extracting postitions of possible
	 * minimum ages in eligibility criterion
	 * @param criterion eligibility criterion
	 * @return list of possible minimum age positions in eligibility criterion
	 */
	private ArrayList<Integer> extractMinimumPositions(String criterion){
		ArrayList<Integer> minimumPositions = new ArrayList<Integer>();
		
		ArrayList<String> minimum = new ArrayList<String>();
		minimum.add("≥");
		minimum.add(">=");
		minimum.add(">");
		minimum.add("minimum");
		minimum.add("min");
		minimum.add("min.");
		minimum.add("least");
		minimum.add("lower");
		minimum.add("greater");
		
		for(String s: minimum){
			if(criterion.contains(s)){
				minimumPositions.add(criterion.indexOf(s));
			}
		}
		
		return minimumPositions;
	}
	
	/**
	 * method for extracting postitions of possible
	 * maximum ages in eligibility criterion
	 * @param criterion eligibility criterion
	 * @return list of possible maximum age positions in eligibility criterion
	 */
	private ArrayList<Integer> extractMaximumPositions(String criterion){
		ArrayList<Integer> maximumPositions = new ArrayList<Integer>();
		
		ArrayList<String> maximum = new ArrayList<String>();
		maximum.add("≤");
		maximum.add("<=");
		maximum.add("<");
		maximum.add("maximum");
		maximum.add("max");
		maximum.add("max.");
		maximum.add("most");
		maximum.add("upper");
		maximum.add("less");
		
		for(String s: maximum){
			if(criterion.contains(s)){
				maximumPositions.add(criterion.indexOf(s));
			}
		}
		
		return maximumPositions;
	}
	
	/**
	 * method for extracting age values from
	 * eligibility criterion
	 * @param criteria eligibility criterion
	 * @return list of extracted age values of an eligibility criterion
	 */
	private ArrayList<AgeValue> extractAgeValues(String criteria){
		// initialize
		ArrayList<AgeValue> ageValues = new ArrayList<AgeValue>();
		
		// extract decimal/ integer values
		Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
		Matcher matcher = regex.matcher(criteria);
		while (matcher.find()) {
			ageValues.add(new AgeValue(matcher.group(1), matcher.start()));
			
		}

		Collections.sort(ageValues, Comparator.comparing(AgeValue::getPosition));

		return ageValues;
	}
	
	/**
	 * method for extraction age units from
	 * free-text eligibility criteria
	 * @param criterion eligibility criterion
	 * @return extracted age units of an eligibility criterion
	 */
	private ArrayList<AgeUnit> extractAgeUnit(String criterion){
		// initialize
		HashSet<AgeUnit> ageUnits = new HashSet<AgeUnit>();
		
		// criteria to lowercase and tokenized
		criterion = criterion.toLowerCase();	
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
		String[] tokenizedCriteria = tokenizer.tokenize(criterion);
		
		// search units and their position in string
		for(String s: tokenizedCriteria){
			if(s.matches("year|years|yr|yrs")){
				Pattern regex = Pattern.compile("year|years|yr|yrs");
				Matcher matcher = regex.matcher(criterion);
				while (matcher.find()) {				
					ageUnits.add(new AgeUnit(AgeUnitsEnum.Years, matcher.start()));
				}
			}else if(s.matches("month|months|mo|mo.|mth|mth.")){
				Pattern regex = Pattern.compile("month|months|mo|mo.|mth|mth.");
				Matcher matcher = regex.matcher(criterion);
				while (matcher.find()) {		
					ageUnits.add(new AgeUnit(AgeUnitsEnum.Months, matcher.start()));	
				}
			}else if(s.matches("week|weeks|wk|wk.")){
				Pattern regex = Pattern.compile("week|weeks|wk|wk.");
				Matcher matcher = regex.matcher(criterion);
				while (matcher.find()) {
					ageUnits.add(new AgeUnit(AgeUnitsEnum.Weeks, matcher.start()));				
				}
			}else if(s.matches("day|days")){
				Pattern regex = Pattern.compile("day|days");
				Matcher matcher = regex.matcher(criterion);
				while (matcher.find()) {
					ageUnits.add(new AgeUnit(AgeUnitsEnum.Days, matcher.start()));				
				}
			}else if(s.matches("hour|hours|hr|hrs|hr.|hrs.")){
				Pattern regex = Pattern.compile("hour|hours|hr|hrs|hr.|hrs.");
				Matcher matcher = regex.matcher(criterion);
				while (matcher.find()) {
					ageUnits.add(new AgeUnit(AgeUnitsEnum.Hours, matcher.start()));				
				}
			}
		}
				
		ArrayList<AgeUnit> ageUnitList = new ArrayList<AgeUnit>(ageUnits);
		Collections.sort(ageUnitList,Comparator.comparing(AgeUnit::getPosition));
		
		return ageUnitList;
	}
}
