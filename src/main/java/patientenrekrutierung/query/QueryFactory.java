package patientenrekrutierung.query;

import java.util.ArrayList;
import java.util.HashSet;

import patientenrekrutierung.datastructure.QueryData;
import patientenrekrutierung.datastructure.demographics.Gender;
import patientenrekrutierung.datastructure.demographics.MinMaxAge;
import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.querybuilding.Criteria;
import patientenrekrutierung.datastructure.querybuilding.MedicalEntity;
import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for creating a CQL query
 * by using the extracted medical and demographic data
 * @author Alexandra Banach
 *
 */
public class QueryFactory {
	
	/**
	 * method for creating a CQL query
	 * by using the extracted medical and demographic data of a study
	 * @param queryData extracted medical and demographic data of a study
	 * @return CQl query
	 * @throws InvalidQueryException 
	 */
	public String createQuery(QueryData queryData ) throws InvalidQueryException{
		// initialize
		HashSet<Subentity> negatedExclusions = new HashSet<Subentity>();
		// get data
		Gender gender = queryData.getGender();
		MinMaxAge minMaxAge = queryData.getMinMaxAge();
		ArrayList<Criteria> inclusionCriterias = queryData.getInclusionCriterias();
		ArrayList<Criteria> exclusionCriterias = queryData.getExclusionCriterias();
		ArrayList<LabCriteria> labsInclusion = queryData.getLabsInclusion(); 
		ArrayList<LabCriteria> labsExclusion = queryData.getLabsInclusion();
		
		// remove exclusion entities which can also be found in inclusion criterias
		for(Criteria exclusionCriteria: exclusionCriterias){
			ArrayList<MedicalEntity> exclusionEntities = exclusionCriteria.getEntityCollection().getMetamapEntities();
			for(MedicalEntity exclusionEntity: new ArrayList<MedicalEntity>(exclusionEntities)){
				for(Criteria inclusionCriteria: inclusionCriterias){
					ArrayList<MedicalEntity> inclusionEntities = inclusionCriteria.getEntityCollection().getMetamapEntities();
					for(MedicalEntity inclusionEntity: inclusionEntities){
								
						if(exclusionEntity.getEntityName().matches(inclusionEntity.getEntityName())){
							exclusionEntities.remove(exclusionEntity);
						}
					}
				}
			}
			exclusionCriteria.getEntityCollection().setMetamapEntities(exclusionEntities);
		}
		
		
		// collect all negated exclusion entities
		for (Criteria criteria : exclusionCriterias) {
			ArrayList<MedicalEntity> entities = criteria.getEntityCollection().getMetamapEntities();
			for (MedicalEntity entity : new ArrayList<MedicalEntity>(entities)) {
				// collect subentities of all negated exclusion criterias and their entities
				if (entity.isNegated()) {
						negatedExclusions.addAll(entity.getSubEntities());
						entities.remove(entity);					
				} else {

				}
			}
		}
		
		// add remaining exclusion criterias to inclusion criterias (unnegated entities)
		inclusionCriterias.addAll(exclusionCriterias);
		
		// solve contradictions: remove exclusion entities from inclusion entities
		for (Criteria inclCriteria : inclusionCriterias) {
			ArrayList<MedicalEntity> inclEntities = inclCriteria.getEntityCollection().getMetamapEntities();
			for (MedicalEntity inclEntity : inclEntities) {
				negatedExclusions.removeAll(inclEntity.getSubEntities());
			}
		}
		
		
		// inclusion criterias-------------------------------------------------
		QueryFactoryInclusion inclusionFactory = new QueryFactoryInclusion();
		String inclusionQuery = inclusionFactory.createInclusionQuery(inclusionCriterias);
		
		
		// exclusion criterias-------------------------------------------------------------
		QueryFactoryExclusion exclusionFactory = new QueryFactoryExclusion();
		String exclusionQuery = exclusionFactory.createExclusionQuery(negatedExclusions);
		
		
		// lab criterias-----------------------------------------------------
		QueryFactoryLabs labFactory = new QueryFactoryLabs();
		String labExclusionQuery = labFactory.createLabQueryExclusion(labsExclusion);
		String labInclusionQuery = labFactory.createLabQueryInclusion(labsInclusion);
		
		
		// build whole query of all parts-----------------------------------
		// extract gender
		String extractedGender = gender.toString().toLowerCase();
		// put all query parts in one list to create query
		ArrayList<String> queryParts = new ArrayList<String>();
		QueryFactoryDemographics demographics = new QueryFactoryDemographics();
		queryParts.add(demographics.createGenderQuery(extractedGender));
		
		try{
			queryParts.add(demographics.createMinAgeQuery(minMaxAge.getMinAge()));
		}catch(Exception e){
			// do nothing, there is no minimum age
		}
		try{
			queryParts.add(demographics.createMaxAgeQuery(minMaxAge.getMaxAge()));
		}catch(Exception e){
			// do nothing, there is no maximum age
		}
			
		queryParts.add(inclusionQuery);
		queryParts.add(exclusionQuery);
		queryParts.add(labInclusionQuery);
		queryParts.add(labExclusionQuery);

		// check if query is valid
		boolean queryIsValid = false;
		for(String s: queryParts){
			if(!s.matches("")){
				queryIsValid = true;
			}
		}
		// if query is not valid throw exception
		if(!queryIsValid){
			throw new InvalidQueryException(
					"No valid CQL query could be generated. Please check your input data!");
		}
		
		String query = "";
		String header = "library Retrieve \n" +
		"using FHIR version '4.0.0' \n"+
		"include FHIRHelpers version '4.0.0' \n \n"+
		"codesystem snomed: 'http://snomed.info/sct' \n"+
		"codesystem loinc: 'http://loinc.org' \n" +
		"context Patient \n \n"+
		"define ProcedureCodes: \n" +
		"\t Flatten([Procedure] P \n" +
		"\t \t return (P.code.coding C return FHIRHelpers.ToCode(C))) \n \n " +
		"define ConditionCodes: \n" +
		"\t Flatten([Condition] C \n" +
		"\t \t return (C.code.coding C return FHIRHelpers.ToCode(C))) \n \n " +
		"define MedicationCodes: \n" +
		"\t Flatten(([MedicationStatement] M return M.medication as CodeableConcept) MC \n" +
		"\t \t return (MC.coding C return FHIRHelpers.ToCode(C))) \n \n " +
		"define InInitialPopulation: \n";
		String end = "";
		
		for(String s: queryParts){
			if(!s.matches("")){
				end = end + s + "\n" + "and ";
			}
		}
		
		
		if(!end.matches("")){
			end = end.substring(0,end.length()-4);
			query = header + end;
		}
		
		return query;
	}
	
	
	
	
}
