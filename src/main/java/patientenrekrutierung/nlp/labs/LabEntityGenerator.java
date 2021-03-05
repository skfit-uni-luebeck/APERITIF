package patientenrekrutierung.nlp.labs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import patientenrekrutierung.datastructure.labs.LabCandidate;
import patientenrekrutierung.datastructure.labs.LabEntity;
import patientenrekrutierung.datastructure.labs.LabUnit;
import patientenrekrutierung.datastructure.labs.LabValue;
import patientenrekrutierung.datastructure.labs.LabValueUnitPair;
import patientenrekrutierung.datastructure.labs.MyComparator;
import patientenrekrutierung.datastructure.ontoserver.Contain;
import patientenrekrutierung.datastructure.querybuilding.EntityType;
import patientenrekrutierung.datastructure.querybuilding.Subentity;
import patientenrekrutierung.nlp.semantics.CodeSearcher;

/**
 * class for creating laboratory entities
 * @author Alexandra Banach
 *
 */
public class LabEntityGenerator {
	/**
	 * method for creating laboratory subentities
	 * using SNOMED CT codes
	 * @param searchTerm name of laboratory entity e.g. leukocyte
	 * @return subentities of laboratory entity containing SNOMED CT codes, name, and entity type
	 * @throws IOException 
	 */
	public HashSet<Subentity> extractSnomedCodes(String searchTerm) throws IOException {
		// initialize
		HashSet<Subentity> snomedSubentities = new HashSet<Subentity>();
		
		// extract subentities from snomed
		CodeSearcher searcher = new CodeSearcher();
		HashSet<Contain> snomedLabs = searcher.searchSnomedLabs(searchTerm);
		
		// put entities found into result list
		if (!snomedLabs.isEmpty()) {
			for (Contain contain : snomedLabs) {
				snomedSubentities.add(new Subentity(contain.getCode(), contain.getDisplay(), EntityType.Lab));
			}
		}
		
		return snomedSubentities;
	}
	
	/**
	 * method for creating laboratory subentities
	 * using LOINC codes
	 * @param searchTerm name of laboratory entity e.g. leukocyte
	 * @return subentities of laboratory entity containing LOINC codes, name, and entity type
	 * @throws IOException 
	 */
	public HashSet<Subentity> extractLoincCodes(String searchTerm) throws IOException{
		// initialize
		HashSet<Subentity> loincSubentities = new HashSet<Subentity>();
		// extract subentities from loinc
		CodeSearcher searcher = new CodeSearcher();
		HashSet<Contain> loincLabs = searcher.searchLoincLabs(searchTerm);

		if (!loincLabs.isEmpty()) {
			for (Contain contain : loincLabs) {
				loincSubentities.add(new Subentity(contain.getCode(), contain.getDisplay(), EntityType.Lab));
			}
		}
		
		return loincSubentities;

	}
	
	/**
	 * method for creating pairs of laboratory units and value from eligibility criterion
	 * @param criterion eligibility criterion
	 * @return list containing pairs of laboratory units and values extracted from eligibility criterion
	 * @throws IOException
	 */
	public ArrayList<LabValueUnitPair> generateLabValueUnits(String criterion) throws IOException{
		// initialize
		ArrayList<LabValueUnitPair> valueUnits = new ArrayList<LabValueUnitPair>();
		
		// extract value
		LabValueExtractor extractor = new LabValueExtractor();
		ArrayList<LabValue> values = extractor.extractLabValue(criterion);
		
		// extract unit
		ArrayList<LabUnit> units = extractor.extractLabUnit(criterion);
		// if no unit was found try again with modified criteria
		if(units.size() == 0){
			criterion = criterion.replaceAll("/", " /");
			units.addAll(extractor.extractLabUnit(criterion));
		}
				
		// reverse lists to traverse backwards
		Collections.reverse(units);
		Collections.reverse(values);
		
		// create pairs of lab values and units
		for (LabUnit un : new ArrayList<LabUnit>(units)) {
			for (LabValue val : new ArrayList<LabValue>(values)) {
				if (val.getPosition() < un.getPosition()) {
					valueUnits.add(new LabValueUnitPair(val, un));
					values.remove(val);
					units.remove(un);
					break;
				}
			}
		}
		
		return valueUnits;
	
	}
	
	/**
	 * method for generating laboratory entities
	 * @param labCandidates potential laboratory entities
	 * @param valueUnits list containing pairs of laboratory values and units
	 * @param comparators comparators for laboratory entities
	 * @return list containing laboratory entities
	 */
	public ArrayList<LabEntity> generateLabEntity(ArrayList<LabCandidate> labCandidates, ArrayList<LabValueUnitPair> valueUnits, ArrayList<MyComparator> comparators) {
		ArrayList<LabEntity> labEntities = new ArrayList<LabEntity>();
		
		// search comparators for labcandidates
		for(LabCandidate l: labCandidates){
			for(MyComparator c: comparators){
				if (l.getPosition() < c.getPosition()){
					l.setComparator(c);
					break;
				}
			}
		}
		
		// search lab value and units for lab candidates
		Collections.reverse(valueUnits);
		Collections.reverse(labCandidates);

		for (LabCandidate candidate : labCandidates) {
			ArrayList<LabValueUnitPair> pairs = new ArrayList<LabValueUnitPair>();
			for (LabValueUnitPair p : new ArrayList<LabValueUnitPair>(valueUnits)) {
				if (p.getLabUnit().getPosition() > candidate.getPosition()) {
					pairs.add(p);
					valueUnits.remove(p);
				}
			}
			
			if(pairs.size() !=0 && candidate.getComparator() != null && (candidate.getLoincCodes().size() !=0 || candidate.getSnomedCodes().size() !=0)){
				labEntities.add(new LabEntity(candidate.getEntityName(), candidate.isNegated(), candidate.getPosition(), candidate.getComparator().getComparatorType(), pairs, candidate.getSnomedCodes(), candidate.getLoincCodes()));
			}

		}
		
		
		return labEntities;
	}
}
