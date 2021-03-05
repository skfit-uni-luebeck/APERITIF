package patientenrekrutierung.nlp.postprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import patientenrekrutierung.datastructure.querybuilding.Conjunction;
import patientenrekrutierung.datastructure.querybuilding.ConjunctionType;
import patientenrekrutierung.datastructure.querybuilding.Criteria;
import patientenrekrutierung.datastructure.querybuilding.MedicalEntity;
/**
 * class for handling negations of entities
 * @author Alexandra Banach
 *
 */
public class NegationDetector {
	
	/**
	 * method for changing negations and conjunctions
	 * for exclusion criteria (De Morgan)
	 * @param criteria list of eligibility criteria
	 * @return list of criteria
	 */
	public ArrayList<Criteria> changeNegationAndConjunction(ArrayList<Criteria> criteria){
		// take each exclusion criteria
		for(Criteria criterion: criteria){
			// negation: or--> and, and--> or
			ArrayList<Conjunction> conjunctions = criterion.getConjunctions();
			for(Conjunction conjunction: conjunctions){
				if(conjunction.getConjunctionType() == ConjunctionType.AND){
					conjunction.setConjunctionType(ConjunctionType.OR);
				}else if(conjunction.getConjunctionType() == ConjunctionType.OR){
					conjunction.setConjunctionType(ConjunctionType.AND);
				}else{
					
				}
			}
			criterion.setConjunctions(conjunctions);
			
			// negation: true--> false, false--> true
			ArrayList<MedicalEntity> entities = criterion.getEntityCollection().getMetamapEntities();
			for(MedicalEntity entity: entities){
				if(entity.isNegated() == true){
					entity.setNegated(false);
				}else if(entity.isNegated() == false){
					entity.setNegated(true);
				}else{
					
				}
			}
			criterion.getEntityCollection().setMetamapEntities(entities);
		}
		
		return criteria;
	}
	
	/**
	 * method for deciding whether extracted
	 * information about patients` gender is negated or not
	 * @param textWithGender eligibility criterion containing information about patients` gender
	 * @return true if gender information is negated else false
	 */
	public boolean isGenderNegated(String textWithGender){
		// set up pipeline properties
	    Properties props = new Properties();
	    // set the list of annotators to run
	    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,natlog");
	    // build pipeline
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    // create a document object
	    CoreDocument document = new CoreDocument(textWithGender);
	    // annnotate the document
	    pipeline.annotate(document);
	    // initialize
	    Boolean isNegated = false;
	    // check polarity for defined entities
	    for (CoreLabel token : document.tokens()) {
	    	String entityText = token.word();
	    	if(entityText.matches("male") || entityText.matches("man") ||entityText.matches("men") || entityText.matches("males") || entityText.matches("woman") || entityText.matches("female") || entityText.matches("women") || entityText.matches("females")){
	    		isNegated = token.get(NaturalLogicAnnotations.PolarityAnnotation.class).isDownwards();
	    	}
	    }
	    
	    return isNegated;
	}
	
	/**
	 * method for setting new negation value
	 * negation detection implemented with Stanford`s
	 * Polarity Annotator 
	 * @param criterion
	 * @return criterion with changed negation information
	 */
	public Criteria setNegations(Criteria criterion){
		// get data
		String text = criterion.getProcessedCriteria();
		ArrayList<MedicalEntity> entities = criterion.getEntityCollection().getMetamapEntities();
		Collections.sort(entities, Comparator.comparing(MedicalEntity::getPosition)); 
		
		// set up pipeline properties
	    Properties props = new Properties();
	    // set the list of annotators to run
	    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,natlog");
	    // build pipeline
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    // create a document object
	    CoreDocument document = new CoreDocument(text);
	    // annnotate the document
	    pipeline.annotate(document);
	    // words are tokenized, do not correspond to metamap entities! [heart] [attack] vs [heart attack]
	    List<CoreLabel> labels = document.tokens();
	    
	    

	    HashSet<MedicalEntity> newEntities = new HashSet<MedicalEntity>();
	    if(entities.size()!= 0 && labels.size() !=0){
	    	for(MedicalEntity e: new ArrayList<MedicalEntity>(entities)){
		    	for(CoreLabel l: new ArrayList<CoreLabel>(labels)){
		    		// check if entity name contains word eg. heart attack contains heart
		    		if(e.getEntityName().contains(l.word())){
		    			// set new negation to entity
						e.setNegated(
								l.get(NaturalLogicAnnotations.PolarityAnnotation.class).isDownwards());
						// put entity into result hashset
						newEntities.add(e);
						// do not use this label again, it was found
						labels.remove(l);
						// do not use entity again, it already got its negation
						entities.remove(e);
					} else {
						
					}

		    	}
		    	
		    }
	    }
	    
	    //convert hashset back to list and return			
	    ArrayList<MedicalEntity> newEntityList = new ArrayList<MedicalEntity>(newEntities);
	    criterion.getEntityCollection().setMetamapEntities(newEntityList);
	
	    
	    
	    return criterion;
	}
	
	
}
