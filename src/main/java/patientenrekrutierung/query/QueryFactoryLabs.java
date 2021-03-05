package patientenrekrutierung.query;

import java.util.ArrayList;
import java.util.HashSet;

import patientenrekrutierung.datastructure.labs.ComparatorType;
import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.labs.LabEntity;
import patientenrekrutierung.datastructure.labs.LabValueUnitPair;
import patientenrekrutierung.datastructure.querybuilding.Conjunction;
import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class to create laboratory CQL queries
 * @author Alexandra Banach
 *
 */
public class QueryFactoryLabs {
	
	/**
	 * method to create CQL query parts for laboratory criteria
	 * @param labCriterias extracted data to create laboratory CQL queries
	 * @return CQL query for laboratory criteria
	 */
	protected String createLabQueryInclusion(ArrayList<LabCriteria> labCriterias){
		// initalize
		String labInclusionQuery = "";
		QueryFactoryLabs queryFactory = new QueryFactoryLabs();
		for(LabCriteria labCriteria: labCriterias){
			ArrayList<LabEntity> labEntities = labCriteria.getLabEntities();
			ArrayList<Conjunction> conjunctions = labCriteria.getConjunctions();
			
			String newQuery = queryFactory.createQueryForLabInclusionCriteria(labEntities, conjunctions);
			if (newQuery != "") {
				labInclusionQuery = labInclusionQuery + newQuery + "\n " + "and";
			}
		
			
		}
		
		if(labInclusionQuery != ""){
			labInclusionQuery = labInclusionQuery.substring(0,labInclusionQuery.length()-4);
		}
		
		
		return labInclusionQuery;
	}
	
	/**
	 * method for creating CQL query part for laboratory criterion
	 * @param labEntities laboratory entities of eligibility criterion
	 * @param conjunctions extracted conjunctions of eligibility criterion
	 * @return CQL query part for laboratory criterion
	 */
	private String createQueryForLabInclusionCriteria(ArrayList<LabEntity> labEntities, ArrayList<Conjunction> conjunctions){
		// initialize
		String labQueryForInclusionCriteria = "";
		QueryFactoryLabs queryFactory = new QueryFactoryLabs();
		
		for(LabEntity labEntity: labEntities){
			String conjunction = "or";
			for(Conjunction conj: conjunctions){
				if(labEntity.getPosition() < conj.getPosition()){
					conjunction = conj.getConjunctionType().toString().toLowerCase();
					break;
				}else{
					
				}
			}
			
			
			String newQuery = queryFactory.createLabQueryForEntity(labEntity);
			if(newQuery != ""){
				labQueryForInclusionCriteria = labQueryForInclusionCriteria + newQuery + "\n \t" + conjunction + " ";
			}
			
		}
		
		int offset = 0;
		if(labQueryForInclusionCriteria.endsWith("and ")){
			offset = 4;
		}else if(labQueryForInclusionCriteria.endsWith("or ")){
			offset = 3;
		}
		
		if(labQueryForInclusionCriteria != ""){
			labQueryForInclusionCriteria = labQueryForInclusionCriteria.substring(0,labQueryForInclusionCriteria.length()- offset);
			labQueryForInclusionCriteria = "(" + labQueryForInclusionCriteria + ")";
		}
		
		
		return labQueryForInclusionCriteria;
	}
	
	
	/**
	 * method for creating CQL query parts for single laboratory entities
	 * @param labEntity laboratory entitiy
	 * @return CQL query part for single laboratory entity
	 */
	private String createLabQueryForEntity(LabEntity labEntity){
		// initialize
		String labEntityQuery = "";
		QueryFactoryLabs queryFactory = new QueryFactoryLabs();
		int counter = 0;
		
		// get data 
		ComparatorType comparator = labEntity.getComparator();
		ArrayList<LabValueUnitPair> valueUnits = labEntity.getValueUnit();
		HashSet<Subentity> snomedCodes = labEntity.getSnomedCodes();
		HashSet<Subentity> loincCodes = labEntity.getLoincCodes();
		
		// create query for exclusion criterias
		if(snomedCodes != null){
			for(Subentity labCode: snomedCodes){
				if(counter < 20){
					labEntityQuery = labEntityQuery + queryFactory.createSingleLabQuery(labCode.getSubEntityCode(), comparator, valueUnits, false, "snomed") + "\n \t \t" + "or ";	
					counter++;
				}else{
					break;
				}
								
			}
		}
		
		if(loincCodes != null){
			for(Subentity labCode: loincCodes){
				if(counter < 20){
					labEntityQuery = labEntityQuery + queryFactory.createSingleLabQuery(labCode.getSubEntityCode(), comparator, valueUnits, false, "loinc") + "\n \t \t" + "or ";
					counter++;
				}else{
					break;
				}
				
			}
		}
		
		if(labEntityQuery != ""){
			labEntityQuery = labEntityQuery.substring(0,labEntityQuery.length()-3);
			labEntityQuery = "(" + labEntityQuery + ")";
		}
		
		
		return labEntityQuery;
	}
	
	/**
	 * method for creating CQL query parts of laboratory exclusion criteria
	 * @param labCriterias laboratory exclusion criteria
	 * @return CQL query part of laboratory exclusion criteria
	 */
	protected String createLabQueryExclusion(ArrayList<LabCriteria> labCriterias){
		// initialize
		String labExclusionQuery = "";
		QueryFactoryLabs queryFactory = new QueryFactoryLabs();
		
		
		
		// take codes of all entities and exclusion criterias
		for(LabCriteria labCriteria: labCriterias){
			ArrayList<LabEntity> labEntities = labCriteria.getLabEntities();
			for(LabEntity labEntity: labEntities){
				// get data 
				ComparatorType comparator = labEntity.getComparator();
				ArrayList<LabValueUnitPair> valueUnits = labEntity.getValueUnit();
				HashSet<Subentity> snomedCodes = labEntity.getSnomedCodes();
				HashSet<Subentity> loincCodes = labEntity.getLoincCodes();
				
				// create query for exclusion criterias
				if(snomedCodes != null){
					for(Subentity labCode: snomedCodes){
						labExclusionQuery = labExclusionQuery + queryFactory.createSingleLabQuery(labCode.getSubEntityCode(), comparator, valueUnits, true, "snomed") + "\n" + "and ";
					}
				}
				
				if(loincCodes != null){
					for(Subentity labCode: loincCodes){
						labExclusionQuery = labExclusionQuery + queryFactory.createSingleLabQuery(labCode.getSubEntityCode(), comparator, valueUnits, true, "loinc") + "\n" + "and ";						
					}
				}
				
				
				labExclusionQuery = labExclusionQuery.substring(0,labExclusionQuery.length()-4);
			}
			
		}
		return labExclusionQuery;
	}
	
	/**
	 * method for creating CQL query parts for single
	 * laboratory subentities
	 * @param labCode code of laboratory subentity
	 * @param comparator comparator of laboratory entity
	 * @param valueUnits laboratory values and units of entity
	 * @param isNegated indicated if laboratory entity is negated or not
	 * @param codeSystem indicated which codesystem is used e.g. SNOMED or LOINC
	 * @return CQL query parts for single laboratory subentities
	 */
	private String createSingleLabQuery(String labCode, ComparatorType comparator, ArrayList<LabValueUnitPair> valueUnits, boolean isNegated, String codeSystem){
		// initalize
		String labQuery = "";
		
		// extract comparator to string
		String comparatorSign = "";
		if(comparator == ComparatorType.GREATER){
			comparatorSign = ">";
		}else if(comparator == ComparatorType.GREATEREQUAL){
			comparatorSign = ">=";
		}else if(comparator == ComparatorType.LESS){
			comparatorSign = "<=";
		}else{
			comparatorSign = "<";
		}
		
		
		// if it is an exclusion criteria
		if(isNegated){
			labQuery = "not ";
		}
		
		// create rest of query
		labQuery = labQuery + 
					"exists( \n \t \t " + 
					"from [Observation: Code '" + labCode + "' from " + codeSystem + "] O \n \t \t" +
					 "where case \n \t \t";
		
		for(LabValueUnitPair p: valueUnits){
			labQuery = labQuery + "when CanConvertQuantity(O.value, '" + p.getLabUnit().getLabUnit() + "') then (O.value as Quantity) " + comparatorSign + " " + p.getLabValue().getLabValue() + " '"+p.getLabUnit().getLabUnit()+"' \n \t \t";
		}
					
		labQuery = labQuery + 
					"else false \n \t \t" +
					"end)";

		return labQuery;
	}
}
