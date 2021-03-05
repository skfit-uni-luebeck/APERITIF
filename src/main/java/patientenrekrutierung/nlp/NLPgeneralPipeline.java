package patientenrekrutierung.nlp;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import opennlp.tools.tokenize.SimpleTokenizer;
import patientenrekrutierung.datastructure.QueryData;
import patientenrekrutierung.datastructure.demographics.Gender;
import patientenrekrutierung.datastructure.demographics.MinMaxAge;
import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.querybuilding.Criteria;
import patientenrekrutierung.nlp.demographics.AgeExtractor;
import patientenrekrutierung.nlp.demographics.GenderExtractor;
import patientenrekrutierung.nlp.postprocessing.ExceptProcessor;
import patientenrekrutierung.nlp.postprocessing.NegationDetector;
import patientenrekrutierung.nlp.semantics.SemanticExtractor;

/**
 * method for processing free-text eligibility criteria
 * and extract data for creating CQL queries
 * @author Alexandra Banach
 *
 */
public class NLPgeneralPipeline extends LanguageProcessor{
	
	/**
	 * method for processing free-text eligibility
	 * criteria and extract data for CQL query generation
	 * @param study free-text eligibility criteria of a study
	 * @return extracted information to create CQL queries
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public QueryData performGeneralPipeline(String study) throws InvocationTargetException, Exception{
		// to lowercase
		study = study.toLowerCase();
		
		// create NLP object for eligibility criteria
		NLPgeneralPipeline nlp = new NLPgeneralPipeline();
		LanguageProcessor languageProcessor = new LanguageProcessor();
		
		// replace pregnancy terms
		study = languageProcessor.replaceTerms(study);
		

		// split inclusion and exclusion criteria
		String[] splitted = languageProcessor.splitEligibilityCriteria(study);
		String[] inclusions = null;
		String[] exclusions = null;
		inclusions = splitted[0].split("\\d+\\. |- ");
		exclusions = splitted[1].split("\\d+\\. |- ");
		

		// gender extraction
		GenderExtractor genderExtractor = new GenderExtractor();
		Gender gender = genderExtractor.extractGender(inclusions);

		// age extraction
		AgeExtractor ageExtractor = new AgeExtractor();
		MinMaxAge minMaxAge = null;
		for(String s: inclusions){
			if((s.toLowerCase().contains("years old")||s.toLowerCase().contains("months old")||s.toLowerCase().contains("up to") || s.toLowerCase().contains("minimum age")|| s.toLowerCase().contains("maximum age")) && minMaxAge == null ){
				minMaxAge = ageExtractor.extractAge(s);
			}
		}
		 if(minMaxAge == null){
			 for(String s: inclusions){
				 SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
					String[] inclusion = tokenizer.tokenize(s);
					// check if word age is contained, then extract age
					for (String st : inclusion) {			
						if ((st.toLowerCase().matches("age") || st.toLowerCase().matches("aged") )&& minMaxAge == null) {
							minMaxAge = ageExtractor.extractAge(s);
						}
					}
			 }
		 }
		

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
}
