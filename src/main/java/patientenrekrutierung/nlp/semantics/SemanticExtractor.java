package patientenrekrutierung.nlp.semantics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import patientenrekrutierung.datastructure.querybuilding.Conjunction;
import patientenrekrutierung.datastructure.querybuilding.Criteria;

/**
 * class for performing semantic extraction of single
 * eligibility criterion
 * @author Alexandra Banach
 *
 */
public class SemanticExtractor {
	/**
	 * method for performing semantic extraction of single
	 * eligibility criterion
	 * @param processedCriterias list of pre-processed eligibility criteria
	 * @return list with extracted information for eligibility criteria
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public ArrayList<Criteria> extractSemantics(ArrayList<String> processedCriteria) throws InvocationTargetException, Exception{
		// initialize
		MetaMapper metamapper = new MetaMapper();	
		ArrayList<Criteria> semantics = new ArrayList<Criteria>();
		for (String criterion : processedCriteria) {
			if(criterion.contains("consent") ||criterion.contains("allergy")|| criterion.contains("hypersensitivity")|| criterion.contains("contraindication")||criterion.contains("eligibility")|| criterion.contains("other trial")|| criterion.contains("other study")|| criterion.contains("other clinical study") || criterion.contains("other clinical trial")){
				
			}else{
				// get conjunctions
				ConjunctionGenerator cons = new ConjunctionGenerator();
				ArrayList<Conjunction> conjuncts = cons.extractConjunctions(criterion);
				// extract semantics
				semantics.add(new Criteria(metamapper.metaMapping(criterion), conjuncts, criterion));
			}
			
		}
		
		return semantics;
	}
}
