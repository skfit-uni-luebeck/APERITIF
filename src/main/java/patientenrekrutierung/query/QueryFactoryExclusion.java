package patientenrekrutierung.query;

import java.util.HashSet;
import patientenrekrutierung.datastructure.querybuilding.EntityType;
import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for creating CQL query parts of exclusion criteria 
 * @author Alexandra Banach
 *
 */
public class QueryFactoryExclusion {
	
	/**
	 * method for creating CQL query parts of exclusion criteria	
	 * @param negatedExclusions codes of medical entities modelling exclusions
	 * @return exclusion criteria CQL query parts
	 */
	public String createExclusionQuery(HashSet<Subentity> negatedExclusions){
		// initialize
		String exclusionQuery = "";
		String procedures = "not exists(ProcedureCodes intersect {";
		String medications = "not exists(MedicationCodes intersect {";
		String conditions = "not exists(ConditionCodes intersect {";
		
		// collect code parts for each category
		for(Subentity s: negatedExclusions){
			if(s.getSubentityType().equals(EntityType.Disease)){
				conditions = conditions + " \n \t Code '"+ s.getSubEntityCode() + "' from snomed,";
			}else if(s.getSubentityType().equals(EntityType.Medication)){
				medications = medications + " \n \t Code '"+ s.getSubEntityCode() + "' from snomed,";
			}else if(s.getSubentityType().equals(EntityType.Procedure)){
				procedures = procedures + " \n \t Code '"+ s.getSubEntityCode() + "' from snomed,";
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
				exclusionQuery = exclusionQuery + "\n and " + medications + "})";
			}else{
				medications = medications + "})";
				exclusionQuery = exclusionQuery + medications;
			}
		}
		if(conditions.endsWith(",")){
			conditions = conditions.substring(0, conditions.length()-1);
			if(exclusionQuery != ""){
				exclusionQuery = exclusionQuery + "\n and " + conditions + "})";
			}else{
				conditions = conditions + "})";
				exclusionQuery = exclusionQuery + conditions;
			}
		}
		
		return exclusionQuery;
	}
		
}
