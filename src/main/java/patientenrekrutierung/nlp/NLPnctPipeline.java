package patientenrekrutierung.nlp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import opennlp.tools.tokenize.SimpleTokenizer;
import patientenrekrutierung.datastructure.NctStudy;
import patientenrekrutierung.datastructure.QueryData;
import patientenrekrutierung.datastructure.demographics.Age;
import patientenrekrutierung.datastructure.demographics.AgeUnit;
import patientenrekrutierung.datastructure.demographics.AgeUnitsEnum;
import patientenrekrutierung.datastructure.demographics.AgeValue;
import patientenrekrutierung.datastructure.demographics.Gender;
import patientenrekrutierung.datastructure.demographics.MinMaxAge;
import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.querybuilding.Criteria;
import patientenrekrutierung.nlp.postprocessing.ExceptProcessor;
import patientenrekrutierung.nlp.postprocessing.NegationDetector;
import patientenrekrutierung.nlp.semantics.SemanticExtractor;

/**
 * class for processing eligibility criteria of a
 * study registered in ClinicalTrials.gov
 * @author Alexandra Banach
 *
 */
public class NLPnctPipeline extends LanguageProcessor{

	
	/**
	 * method to process eligibility criteria of a
	 * study registered in ClinicalTrials.gov.
	 * Minimum, maximum age and gender of patients
	 * are extracted from the corresponding fields of
	 * the study data
	 * @param study extracted study data from ClinicalTrials.gov
	 * @return processed data to create CQL query
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public QueryData performNCTPipeline(NctStudy study) throws InvocationTargetException, Exception{

		String eligibility = study.getEligibilityCriteria();
		
		// to lowercase
		eligibility = eligibility.toLowerCase();
	
		// replace pregnancy terms
		LanguageProcessor processor = new LanguageProcessor();
		eligibility = processor.replaceTerms(eligibility);
		
		// create NLP object for eligibility criteria
		NLPnctPipeline nlp = new NLPnctPipeline();
		
		//gender extraction
		String genderFromTag = study.getGender().toLowerCase();
		Gender gender = null;
		if(genderFromTag.matches("female")){
			gender = Gender.FEMALE;
		}else if(genderFromTag.matches("male")){
			gender = Gender.MALE;
		}else{
			gender = Gender.ALL;
		}
		
		// age extraction
		Age minAge = nlp.ageExtractionNCT(study.getMinimumAge());
		Age maxAge = nlp.ageExtractionNCT(study.getMaximumAge());
		MinMaxAge minMaxAge = new MinMaxAge(minAge, maxAge);
	
		// split inclusion and exclusion criteria
		LanguageProcessor nlp2 = new LanguageProcessor();
		String[] splitted = nlp2.splitEligibilityCriteria(eligibility);
		String[] inclusions = null;
		String[] exclusions = null;
		
		
		inclusions = splitted[0].split("\\d+\\. |- ");
		exclusions = splitted[1].split("\\d+\\. |- ");
		
				
		// process each criteria array
		ArrayList<String> processedInclusions = nlp.processCriteria(inclusions);
		ArrayList<String> processedExclusions = nlp.processCriteria(exclusions);
		
		// search entities and conjunctions
		SemanticExtractor semanticExtractor = new SemanticExtractor();
		ArrayList<Criteria> extractedSemanticsInclusion = semanticExtractor.extractSemantics(processedInclusions);
		ArrayList<Criteria> extractedSemanticsExclusion = semanticExtractor.extractSemantics(processedExclusions);
		
		// process conjunction except: delete snomed codes
		ExceptProcessor exceptProcessor = new ExceptProcessor();
		ArrayList<Criteria> exceptedExclusion = exceptProcessor.exceptProcessing(extractedSemanticsExclusion);
		ArrayList<Criteria> exceptedInclusion = exceptProcessor.exceptProcessing(extractedSemanticsInclusion);
		
		// negate exclusion
		NegationDetector negationDetector = new NegationDetector();
		ArrayList<Criteria> negatedExclusion = negationDetector.changeNegationAndConjunction(exceptedExclusion);

		// collect lab criteria
		ArrayList<LabCriteria> labsExclusion = new ArrayList<LabCriteria>();
		ArrayList<LabCriteria> labsInclusion = new ArrayList<LabCriteria>();
		for (Criteria c : exceptedInclusion) {
			labsInclusion.add(c.getEntityCollection().getLabCriteria());
		}
		for (Criteria c : exceptedExclusion) {
			labsExclusion.add(c.getEntityCollection().getLabCriteria());
		}
		
		// return complete result
		return new QueryData(gender, minMaxAge, exceptedInclusion, negatedExclusion, labsInclusion, labsExclusion);
	}
	
	/**
	 * method to extract a patient`s minimum and
	 * maximum age from fields of NCT data
	 * @param age minimum or maximum age from data of a NCT study
	 * @return extracted age information
	 * @throws IOException
	 */
	private Age ageExtractionNCT(String age) throws IOException{
			
		// preprocessing 
		// to lowercase
		age = age.toLowerCase();
		// tokenize
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;  
		String[] tokenized = tokenizer.tokenize(age);
		// lemmatize
		NLPnctPipeline nlp = new NLPnctPipeline();
		String[] ageLemmatized = nlp.lemmatize(tokenized);
		
		// extract value and unit
		Age processedAge = new Age(null, null);
		AgeUnitsEnum ageUnitsEnum = null;
		int unitPosition = 0;
		
		for(String s: ageLemmatized){
			if(s.matches("year")){
				ageUnitsEnum = AgeUnitsEnum.Years;
				unitPosition = age.indexOf(s);
			}else if(s.matches("month")){
				ageUnitsEnum = AgeUnitsEnum.Months;
				unitPosition = age.indexOf(s);
			}else if(s.matches("week")){
				ageUnitsEnum = AgeUnitsEnum.Weeks;
				unitPosition = age.indexOf(s);
			}else if(s.matches("day")){
				ageUnitsEnum = AgeUnitsEnum.Days;
				unitPosition = age.indexOf(s);
			}else if(s.matches("hour")){
				ageUnitsEnum = AgeUnitsEnum.Hours;
				unitPosition = age.indexOf(s);
			}else{
				
			}
			
			if(s.matches("\\d+")){
				processedAge.setAgeValue(new AgeValue(s, age.indexOf(s)));
			}
				
		}
		
		processedAge.setAgeUnit(new AgeUnit(ageUnitsEnum, unitPosition));			
		
		return processedAge;
	}
}
