package patientenrekrutierung.nlp.semantics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.nih.nlm.nls.metamap.lite.types.Entity;
import gov.nih.nlm.nls.metamap.lite.types.Ev;
import patientenrekrutierung.datastructure.EntityCollection;
import patientenrekrutierung.datastructure.labs.LabCandidate;
import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.labs.LabEntity;
import patientenrekrutierung.datastructure.labs.LabValueUnitPair;
import patientenrekrutierung.datastructure.labs.MyComparator;
import patientenrekrutierung.datastructure.querybuilding.EntityType;
import patientenrekrutierung.datastructure.querybuilding.MedicalEntity;
import patientenrekrutierung.datastructure.querybuilding.Subentity;
import patientenrekrutierung.nlp.labs.ComparatorGenerator;
import patientenrekrutierung.nlp.labs.LabEntityGenerator;

/**
 * class for generating collection of medical enitities
 * (e.g. diagnoses, medications, procedures or laboratory results)
 * @author Alexandra Banach
 *
 */
public class EntityGenerator {
	/**
	 * method for generating collection of medical enitities
	 * (e.g. dC)
	 * @param entityList list of extracted medical entities
	 * @param criterion eligibility criterion
	 * @return entity collection with all medical entities (e.g. diagnoses, medications, procedures or laboratory results) of an eligibility criterion
	 * @throws IOException
	 */
	public EntityCollection generateEntity(List<Entity> entityList, String criterion) throws IOException{
		// initialize
		HashSet<MedicalEntity> metamapEntities = new HashSet<MedicalEntity>();
		ArrayList<LabEntity> labEntities = new ArrayList<LabEntity>();
		ArrayList<LabCandidate> labCandidates = new ArrayList<LabCandidate>();
		
		// blacklist, do not search these terms
		Blacklist blackList = new Blacklist();
		ArrayList<String> blacklist = blackList.getBlacklist();
		
		// for each entity get subentities
		for(Entity entity: entityList){
			// collect semantic types and matched texts of all subentities
			Set<Ev> subs = entity.getEvSet();
			HashSet<String> matchedTexts = new HashSet<String>();
			Set<String> semanticTypes = new HashSet<String>();
			for (Ev sub : subs) {
				if (!blacklist.contains(sub.getMatchedText().toLowerCase())) {
					semanticTypes.addAll(sub.getConceptInfo().getSemanticTypeSet());
					matchedTexts.add(sub.getMatchedText().toLowerCase());
					if (sub.getConceptInfo().getPreferredName() != "") {
						matchedTexts.add(sub.getConceptInfo().getPreferredName().toLowerCase());
					}
				}

			}

			// convert Types to Enum EntityTypes
			CodeSearcher searcher = new CodeSearcher();
			// no type for pregnancy and breastfeeding--> add Disease to ensure result
			Set<EntityType> entityTypes = searcher.extractEntityTypes(semanticTypes);
		
			if(entityTypes.isEmpty() && entity.getMatchedText().matches("pregnancy|breast feeding|breast feed")){
				entityTypes.add(EntityType.Disease);
			}
			
			
			SubentityGenerator subGenerator = new SubentityGenerator();
			HashSet<Subentity> metamapSubentities = subGenerator.generateSubentities(entity, entityTypes, matchedTexts, blacklist, criterion);
			if(!metamapSubentities.isEmpty()){
				metamapEntities.add(new MedicalEntity(entity.isNegated(), entity.getMatchedText(), metamapSubentities,
						entity.getStart(), entityTypes));
			}else{
				
			}
			//labs
			Set<Ev> evSet = entity.getEvSet();
			boolean isLab = false;
			for(Ev e: evSet){
				Set<String> semantics = e.getConceptInfo().getSemanticTypeSet();
				for(String s: semantics){
					if(s.matches("lbtr|lbpr|cell|bacs|aapp")){
						isLab = true;
					}else{
						
					}
				}
			}
			
			
			if(isLab){
				LabEntityGenerator labGenerator = new LabEntityGenerator();
				HashSet<Subentity> snomedCodes = labGenerator.extractSnomedCodes(entity.getMatchedText());
				HashSet<Subentity> loincCodes = labGenerator.extractLoincCodes(entity.getMatchedText());
				
				if(snomedCodes != null || loincCodes != null){
					labCandidates.add(new LabCandidate(entity.getMatchedText(), entity.isNegated(), entity.getStart(), snomedCodes, loincCodes));
				}
			}
			
		}
		
		// extract lab values and units
		LabEntityGenerator labGenerator = new LabEntityGenerator();
		ArrayList<LabValueUnitPair> valueUnits = labGenerator.generateLabValueUnits(criterion);
		
		// extract lab comparators
		ComparatorGenerator comparatorGenerator = new ComparatorGenerator();
		ArrayList<MyComparator> comparators = comparatorGenerator.extractComparator(criterion);
		
		// create lab entities from lab candidates
		labEntities.addAll(labGenerator.generateLabEntity(labCandidates, valueUnits, comparators));
		
		// extract conjunctions
		ConjunctionGenerator conjunctionGenerator = new ConjunctionGenerator();
		// convert labEntities from hashset to list
		Collections.sort(labEntities, Comparator.comparing(LabEntity::getPosition));
		HashSet<LabEntity> labEntitiesAsHashSet = new HashSet<LabEntity>(labEntities);
		ArrayList<LabEntity> labEntitiesAsList = new ArrayList<LabEntity>(labEntitiesAsHashSet);
		Collections.sort(labEntitiesAsList, Comparator.comparing(LabEntity::getPosition));
		LabCriteria labCriteria = new LabCriteria(labEntitiesAsList, conjunctionGenerator.extractConjunctions(criterion));
		
		// from HashSet to list, sorted by position of entity
		ArrayList<MedicalEntity> metamapEntitiesAsList = new ArrayList<MedicalEntity>(metamapEntities);		
		Collections.sort(metamapEntitiesAsList, Comparator.comparing(MedicalEntity::getPosition));
		
		return new EntityCollection(metamapEntitiesAsList, labCriteria);
	}
}
