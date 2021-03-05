package patientenrekrutierung.nlp.demographics;

import java.lang.reflect.InvocationTargetException;

import opennlp.tools.tokenize.SimpleTokenizer;
import patientenrekrutierung.datastructure.demographics.Gender;
import patientenrekrutierung.nlp.postprocessing.NegationDetector;

/**
 * class for realizing extraction of 
 * gender information from free-text eligibility criteria
 * @author Alexandra Banach
 *
 */
public class GenderExtractor {
	/**
	 * extracts gender information from 
	 * free-text eligibility criteria
	 * @param inclusionCriteria inclusion criteria
	 * @return extracted gender information
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public Gender extractGender(String[] inclusionCriteria) throws InvocationTargetException, Exception{
		// initialize
		Gender gender = Gender.ALL;
		Gender female = null;
		Gender male = null;
		
		if(inclusionCriteria.length > 0){
			for(String criteria: inclusionCriteria){
				// all data to lowercase
				criteria = criteria.toLowerCase();
				// tokenize
				SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
				String[] tokenizedCriteria = tokenizer.tokenize(criteria);
				
				// checking gender in study
				for(String s: tokenizedCriteria){
					// check if female
					if(s.matches("female") || s.matches("woman") || s.matches("women") || s.matches("females")){
						female = Gender.FEMALE;
					}
					// check if male
					if(s.matches("male") || s.matches("man") || s.matches("males") || s.matches("men")){
						male = Gender.MALE;
					}
					if(s.matches("malis")){
						male = Gender.MALE;
						// correct lemmatization mistake
						s = "males";
					}
					
				}
				
				
				// check which gender variable to return
				if(female != null && male != null){
					gender = Gender.ALL;
				}else if(female != null){
					gender = Gender.FEMALE;
				}else if (male != null){
					gender = Gender.MALE;
				}else{
					gender = Gender.ALL;
				}
				
				// check if gender is negated
				NegationDetector negDetector = new NegationDetector();
				boolean isNegated = negDetector.isGenderNegated(criteria);
				
				
				if(isNegated && gender == Gender.MALE){
					gender = Gender.FEMALE;
				} else if(isNegated && gender == Gender.FEMALE){
					gender = Gender.MALE;
				}
			}
			
		}
		return gender;
	}
	
}
