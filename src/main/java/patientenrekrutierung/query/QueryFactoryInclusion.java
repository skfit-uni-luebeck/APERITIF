package patientenrekrutierung.query;

import java.util.ArrayList;
import java.util.HashSet;

import patientenrekrutierung.datastructure.querybuilding.Conjunction;
import patientenrekrutierung.datastructure.querybuilding.Criteria;
import patientenrekrutierung.datastructure.querybuilding.EntityType;
import patientenrekrutierung.datastructure.querybuilding.MedicalEntity;
import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for creating CQL query parts for medical entities
 * of inclusion criteria
 * @author Alexandra Banach
 *
 */
public class QueryFactoryInclusion {
	/**
	 * method for creating CQL query parts for medical entities
	 * of inclusion criteria
	 * @param inclusionCriteria list of inclusion criteria and the extracted data
	 * @return CQL query part for inclusion criteria
	 */
	protected String createInclusionQuery(ArrayList<Criteria> inclusionCriteria) {
		// get query part for procedures, medications and disorders
		QueryFactoryInclusion queryFacIncl	 = new QueryFactoryInclusion();
		ArrayList<String> criteriaQueries = new ArrayList<String>();
		// take each criteria
		for (Criteria c : inclusionCriteria) {
			ArrayList<MedicalEntity> entities = c.getEntityCollection().getMetamapEntities();
			// create query for each entity
			for (MedicalEntity e : entities) {
				e.setQueryForEntity(queryFacIncl.createInclusionQueryForEntity(e));					
			}
			// with all entities create query for each criteria
			criteriaQueries.add(queryFacIncl.createQueryCriteria(c));
		}
		// create one querypart for all criterias with procedures, medications
		// and disorders
		String entityQuery = queryFacIncl.createQueryForEligibility(criteriaQueries);

		return entityQuery;
	}
	
	/**
	 * method for creating CQL query parts for single medical
	 * entities
	 * @param entity medical entity
	 * @return CQL query part for medical entity
	 */
	private String createInclusionQueryForEntity(MedicalEntity entity){
				
		// initialize
		String exclusionQuery = "";
		String procedures = "exists(ProcedureCodes intersect {";
		String medications = "exists(MedicationCodes intersect {";
		String conditions = "exists(ConditionCodes intersect {";
		String conjunction = "or";
		HashSet<Subentity> subEntities = entity.getSubEntities();
		
		// if entity is negated add not to query, change conjunction
		if(entity.isNegated()){
			procedures = "not exists(ProcedureCodes intersect {";
			medications = "not exists(MedicationCodes intersect {";
			conditions = "not exists(ConditionCodes intersect {";
			conjunction = "and";
		}
				
		// collect code parts for each category
		for(Subentity s: subEntities){
			if(s.getSubentityType().equals(EntityType.Disease)){
				conditions = conditions + " \n \t \t Code '"+ s.getSubEntityCode() + "' from snomed,";
			}else if(s.getSubentityType().equals(EntityType.Medication)){
				medications = medications + "\n \t \t Code '"+ s.getSubEntityCode() + "' from snomed,";
			}else if(s.getSubentityType().equals(EntityType.Procedure)){
				procedures = procedures + "\n \t \t Code '"+ s.getSubEntityCode() + "' from snomed,";
			}else{
				
			}
		}
		
		
		// cut off last , it is not needed
		// add }) to complete statement
		// put statements of categories together using and
		if(procedures.endsWith(",")){
			procedures = procedures.substring(0, procedures.length()-1);
			procedures = procedures + "})";
			exclusionQuery = exclusionQuery + procedures;
		}
		if(medications.endsWith(",")){
			medications = medications.substring(0, medications.length()-1);
			if(exclusionQuery != ""){
				exclusionQuery = exclusionQuery + "\n \t \t" + conjunction + " " + medications + "})";
			}else{
				medications = medications + "})";
				exclusionQuery = exclusionQuery + medications;
			}
		}
		if(conditions.endsWith(",")){
			conditions = conditions.substring(0, conditions.length()-1);
			if(exclusionQuery != ""){
				exclusionQuery = exclusionQuery + "\n \t \t" + conjunction + " " + conditions + "})";
			}else{
				conditions = conditions + "})";
				exclusionQuery = exclusionQuery + conditions;
			}
		}
		
		if(exclusionQuery != ""){
			exclusionQuery = "(" + exclusionQuery + ")";
		}
		
		return exclusionQuery;
	}
	
	/**
	 * method for creating CQL query parts of inclusion criterion
	 * @param criterion inclusion criterion
	 * @return  CQL query part of inclusion criterion
	 */
	private String createQueryCriteria(Criteria criterion) {
		// initialize
		String queryForCriteria = "";
		ArrayList<MedicalEntity> entities = criterion.getEntityCollection().getMetamapEntities();
		ArrayList<Conjunction> conjunctions = criterion.getConjunctions();

		for (int i = 0; i < entities.size(); i++) {
			// extract conjunction
			String conjunction = "or";
			for (Conjunction conj : conjunctions) {
				if (entities.get(i).getPosition() < conj.getPosition()) {
					conjunction = conj.getConjunctionType().toString().toLowerCase();
					break;
				} else {

				}
			}
			// get query for single entity
			String queryForEntity = entities.get(i).getQueryForEntity();
			if (queryForEntity == null || queryForEntity == "") {

			} else {
				// concatenate entity queries with extracted conjunction
				queryForCriteria = queryForCriteria + queryForEntity + "\n \t " + conjunction;
			}

		}

		// cut off last conjunction, it is not needed
		if (queryForCriteria.endsWith("and")) {
			queryForCriteria = queryForCriteria.substring(0, queryForCriteria.length() - 3);
		} else if (queryForCriteria.endsWith("or")) {
			queryForCriteria = queryForCriteria.substring(0, queryForCriteria.length() - 2);
		} else {

		}

		// add brackets
		if (queryForCriteria != "") {
			queryForCriteria = "\t (" + queryForCriteria + ")";
		}

		return queryForCriteria;
	}

	/**
	 * method for connecting single inclusion criteria
	 * via junctor "and"
	 * @param queriesForCriterias list containing CQL query parts for inclusion criteria
	 * @return CQL query part for inclusion criteria
	 */
	public String createQueryForEligibility(ArrayList<String> queriesForCriterias) {
		String queryForCriteria = "";
		for (String s : queriesForCriterias) {
			if (s != "") {
				queryForCriteria = queryForCriteria + s + "\n and ";
			} else {

			}

		}

		if (queryForCriteria != "") {
			queryForCriteria = queryForCriteria.substring(0, queryForCriteria.length() - 4);
		}

		return queryForCriteria;
	}

}
