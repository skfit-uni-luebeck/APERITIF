package patientenrekrutierung.nlp.postprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import patientenrekrutierung.datastructure.EntityCollection;
import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.querybuilding.Conjunction;
import patientenrekrutierung.datastructure.querybuilding.ConjunctionType;
import patientenrekrutierung.datastructure.querybuilding.Criteria;
import patientenrekrutierung.datastructure.querybuilding.MedicalEntity;
import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for performing except processing
 * @author Alexandra Banach
 *
 */
public class ExceptProcessor {
	
	/**
	 * method for performing except processing of eligibility criteria
	 * @param criterias
	 * @return
	 */
	public ArrayList<Criteria> exceptProcessing(ArrayList<Criteria> criteria){
		// initialize
		ArrayList<Criteria> processedCriteria = new ArrayList<Criteria>();
		ExceptProcessor processor = new ExceptProcessor();
		
		for(Criteria c: criteria){
			// check if criteria contains conjunction except
			ArrayList<ConjunctionType> cons = new ArrayList<ConjunctionType>();
			for(Conjunction con: c.getConjunctions()){
				cons.add(con.getConjunctionType());
			}
			
			// if true process criteria and add to result list
			if(cons.contains(ConjunctionType.EXCEPT)){
				processedCriteria.add(processor.processCriteria(c));
			}else{
				// else take unprocessed criteria
				processedCriteria.add(c);
			}	
		}
			
		return processedCriteria;
	}
	
	/**
	 * method for performing except processing of single criterion
	 * @param criterion eligibility criterion containing word "except"
	 * @return processed criterion
	 */
	public Criteria processCriteria(Criteria criterion){
		// negation extraction with Stanford (better than Metamap Lite for except)
		NegationDetector neg = new NegationDetector();
		neg.setNegations(criterion);
		
		// initialize
		ArrayList<MedicalEntity> newEntities = new ArrayList<MedicalEntity>();
		ArrayList<Conjunction> newConjunctions = new ArrayList<Conjunction>();
		
		// get data
		ArrayList<Conjunction> conjunctions = criterion.getConjunctions();
		ArrayList<MedicalEntity> entities = criterion.getEntityCollection().getMetamapEntities();
		// sort entities by position
		Collections.sort(entities, Comparator.comparing(MedicalEntity::getPosition)); 
		
		// search excepts in conjunctions
		ArrayList<Conjunction> excepts = new ArrayList<Conjunction>();
		for(Conjunction c: conjunctions){
			if(c.getConjunctionType() == ConjunctionType.EXCEPT){
				excepts.add(c);
			}
		}
		
	
		// search MetamapEntities before except
		ArrayList<MedicalEntity> beforeExcept = new ArrayList<MedicalEntity>();
		// for each except found
		for (int j = excepts.size(); j-- > 0;) {
			// search in all entities
			for (int i = entities.size(); i-- > 0;) {
				if (entities.get(i).getPosition() < excepts.get(j).getPosition()) {
					// entity before except found
					beforeExcept.add(entities.get(i));
					// stop search 
					break;
				} else {
				}
			}
		}
		
		// search entities after except and remove codes from entities before except
		ArrayList<Integer> positionsOfEntitiesToRemove = new ArrayList<Integer>();
		for(MedicalEntity e: beforeExcept){
			// iterate over all entities
			for(int i=0; i<entities.size(); i++){
				// if entity matches with entity before except
				if(e.getPosition() == entities.get(i).getPosition()){
					// collect codes to be removed
					HashSet<Subentity> subsToRemove = new HashSet<Subentity>();
					// make sure that the end of the list is not reached
					int j = i;
					if(j< entities.size()-1){
						j++;
					}
					// check for entities after except (other negation than entity before except)
					// && entities.get(j).isNegated() != entities.get(i).isNegated()
					while (j < entities.size()) {
						// collect codes to be removed and collect position of entities
						subsToRemove.addAll(entities.get(j).getSubEntities());
						positionsOfEntitiesToRemove.add(entities.get(j).getPosition());
						j++;
					}
					// remove codes and put new list to entity object
					HashSet<Subentity> subsToStay = entities.get(i).getSubEntities();
					ExceptProcessor processor = new ExceptProcessor();
					HashSet<Subentity> newSubs = processor.removeSubs(subsToStay, subsToRemove);
					entities.get(i).setSubEntities(newSubs);
					
				}
			}
		
		}
		
		// collect entities without the ones after except
		for(MedicalEntity e: entities){
			if(!positionsOfEntitiesToRemove.contains(e.getPosition())){
				newEntities.add(e);
			}
		}
		
		// extract remaining conjunctions
		for(MedicalEntity e: newEntities){
			// find conjunction before each remaining entity
			for(int i = conjunctions.size(); i-- > 0;){
				if(conjunctions.get(i).getPosition()< e.getPosition()){
					newConjunctions.add(conjunctions.get(i));
					break;
				}
			}
		}
				
		// return new modified criteria
		ArrayList<MedicalEntity> entityList = new ArrayList<MedicalEntity>(newEntities);
		LabCriteria lab = criterion.getEntityCollection().getLabCriteria();
		EntityCollection collection = new EntityCollection(entityList, lab);
		Criteria newCriteria = new Criteria(collection, newConjunctions, criterion.getProcessedCriteria());
	
		
		return newCriteria;
	}
	
	/**
	 * method which removes subentities after "except"
	 * from set of subentities before "except"
	 * @param subsToStay subentities of entity before "except", they should not be removed
	 * @param subsToRemove subentities of entity after "except", they should be removed
	 * @return set of subentities where subentities after "except" were removed
	 */
	public HashSet<Subentity> removeSubs(HashSet<Subentity> subsToStay, HashSet<Subentity> subsToRemove){
		// collect codes that should be removed
		HashSet<String> codesToRemove = new HashSet<String>();
		for(Subentity sub: subsToRemove){
			codesToRemove.add(sub.getSubEntityCode());
		}
		
		// check if code of entity is in codesToRemove
		HashSet<Subentity> subResult = new HashSet<Subentity>();
		for(Subentity sub: subsToStay){
			// if not put object into result list
			if(!codesToRemove.contains(sub.getSubEntityCode())){
				subResult.add(sub);
			}
		}
		
		return subResult;
	}
	
	
}
