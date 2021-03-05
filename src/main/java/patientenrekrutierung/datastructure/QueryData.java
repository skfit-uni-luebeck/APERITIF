package patientenrekrutierung.datastructure;

import java.util.ArrayList;

import patientenrekrutierung.datastructure.demographics.Gender;
import patientenrekrutierung.datastructure.demographics.MinMaxAge;
import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.querybuilding.Criteria;

/**
 * class for modelling the extracted data for 
 * CQL query generation
 * @author Alexandra Banach
 *
 */
public class QueryData {
	/**
	 * gender of a patient
	 */
	private Gender gender;
	/**
	 * minimum and maximum age of patients
	 */
	private MinMaxAge minMaxAge;
	/**
	 * inclusion criteria of a study
	 */
	private ArrayList<Criteria> inclusionCriteria;
	/**
	 * exclusion criteria of a study
	 */
	private ArrayList<Criteria> exclusionCriteria;
	/**
	 * laboratory results of inclusion criteria
	 */
	private ArrayList<LabCriteria> labsInclusion;
	/**
	 * laboratory results of exclusion criteria
	 */
	private ArrayList<LabCriteria> labsExclusion;
	
	/**
	 * Constructor for creating query data
	 * @param gender of a patient
	 * @param minMaxAge minimum and maximum age of patient
	 * @param inclusionCriterias information extracted from inclusion criteria
	 * @param exclusionCriterias information extracted from exclusion criteria
	 * @param labsInclusion laboratory results from inclusion criteria
	 * @param labsExclusion laboratory results from exclusion criteria
	 */
	public QueryData(Gender gender, MinMaxAge minMaxAge, ArrayList<Criteria> inclusionCriteria, ArrayList<Criteria> exclusionCriteria, ArrayList<LabCriteria> labsInclusion, ArrayList<LabCriteria> labsExclusion){
		this.gender = gender;
		this.minMaxAge = minMaxAge;
		this.inclusionCriteria = inclusionCriteria;
		this.exclusionCriteria = exclusionCriteria;
		this.labsInclusion = labsInclusion;
		this.labsExclusion = labsExclusion;
	}
	
	/**
	 * gets gender of patients
	 * @return gender of patients
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * sets gender of patients
	 * @param gender of patients
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	/**
	 * gets minimum and maximum age of patients
	 * @return minimum and maximum age of patients
	 */
	public MinMaxAge getMinMaxAge() {
		return minMaxAge;
	}
	
	/**
	 * sets minimum and maximum age of patients
	 * @param minMaxAge minimum and maximum age of patients
	 */
	public void setMinMaxAge(MinMaxAge minMaxAge) {
		this.minMaxAge = minMaxAge;
	}
	
	/**
	 * gets inclusion criteria 
	 * @return inclusion criteria
	 */
	public ArrayList<Criteria> getInclusionCriterias() {
		return inclusionCriteria;
	}
	
	/**
	 * sets inclusion criteria
	 * @param inclusionCriterias inclusion criteria
	 */
	public void setInclusionCriterias(ArrayList<Criteria> inclusionCriteria) {
		this.inclusionCriteria = inclusionCriteria;
	}
	
	/**
	 * gets exclusion criteria
	 * @return exclusion criteria 
	 */
	public ArrayList<Criteria> getExclusionCriterias() {
		return exclusionCriteria;
	}
	
	/**
	 * sets exclusion criteria
	 * @param exclusionCriteria exclusion criteria
	 */
	public void setExclusionCriterias(ArrayList<Criteria> exclusionCriteria) {
		this.exclusionCriteria = exclusionCriteria;
	}
	
	/**
	 * gets laboratory results of inclusion criteria
	 * @return laboratory results of inclusion criteria
	 */
	public ArrayList<LabCriteria> getLabsInclusion() {
		return labsInclusion;
	}
	
	/**
	 * sets laboratory results of inclusion criteria
	 * @param labsInclusion
	 */
	public void setLabsInclusion(ArrayList<LabCriteria> labsInclusion) {
		this.labsInclusion = labsInclusion;
	}
	
	/**
	 * gets laboratory results of exclusion criteria
	 * @return laboratory results of exclusion criteria
	 */
	public ArrayList<LabCriteria> getLabsExclusion() {
		return labsExclusion;
	}
	
	/**
	 * sets laboratory results of exclusion criteria
	 * @param labsExclusion laboratory results of exclusion criteria
	 */
	public void setLabsExclusion(ArrayList<LabCriteria> labsExclusion) {
		this.labsExclusion = labsExclusion;
	}
}
