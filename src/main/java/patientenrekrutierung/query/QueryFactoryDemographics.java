package patientenrekrutierung.query;

import patientenrekrutierung.datastructure.demographics.Age;
import patientenrekrutierung.datastructure.demographics.AgeUnitsEnum;

/**
 * class for creating CQl query parts
 * based on the extracted demographic data of 
 * a study (minimum, maximum age and gender of patients)
 * @author Alexandra Banach
 *
 */
public class QueryFactoryDemographics {
	
	/**
	 * method for creating CQl query part
	 * based on the extracted gender information of a study
	 * @param gender extracted gender information of a study
	 * @return gender query part of a study
	 */
	protected String createGenderQuery(String gender){
		String genderQuery = "";
		if(gender.matches("female") || gender.matches("male")){
			genderQuery =  "Patient.gender = '"+ gender + "'";
		}else{
			
		}
		return genderQuery;
	}
	
	/**
	 * method for creating CQl query part
	 * based on the extracted minimum age of patients
	 * from the study data
	 * @param minimumAge minimum age of patients
	 * @return CQL query part for minimum age of patients
	 */
	protected String createMinAgeQuery(Age minimumAge){
		// get min value
		String minValue = "";
		try{
			minValue = minimumAge.getAgeValue().getAgeValue();
		}catch(Exception e){
			// minValue stays empty
		}
			
		// get min unit
		AgeUnitsEnum minUnit = null;
		try{
			minUnit = minimumAge.getAgeUnit().getAgeUnit();
		}catch(Exception e){
			// minUnit stays null
		}
		
		// initialize query
		String minAgeQuery = "";
		
		// create query
		if(minUnit != null && !minValue.matches("")){
			minAgeQuery = "AgeIn"+minUnit.toString()+"At(Today()) >=" + minValue;
		}
			
		return minAgeQuery;
	}
	
	/**
	 * method for creating CQl query part
	 * based on the extracted maximum age of patients
	 * from the study data
	 * @param maximumAge maximum age of patients
	 * @return CQL query part for maximum age of patients
	 */
	protected String createMaxAgeQuery(Age maximumAge){
		// get max value
		String maxValue = "";
		try {
			maxValue = maximumAge.getAgeValue().getAgeValue();
		} catch (Exception e) {
			// maxValue stays empty
		}

		// get max unit
		AgeUnitsEnum maxUnit = null;
		try {
			maxUnit = maximumAge.getAgeUnit().getAgeUnit();
		} catch (Exception e) {
			// maxUnit stays null
		}

		// initialize query
		String maxAgeQuery = "";
		
		// create query
		if(maxUnit != null && !maxValue.matches("")){
			maxAgeQuery = "AgeIn"+ maxUnit.toString() + "At(Today()) <=" + maxValue;
		}
		
		
		return maxAgeQuery;
	}
}
