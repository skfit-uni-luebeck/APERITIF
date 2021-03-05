package patientenrekrutierung.datastructure;

import java.util.ArrayList;

import patientenrekrutierung.datastructure.demographics.Gender;
import patientenrekrutierung.datastructure.demographics.MinMaxAge;

/**
 * class for modelling data storage for pre-processed 
 * eligibility criteria
 * @author Alexandra Banach
 *
 */
public class ProcessedCriteria {
	/**
	 * pre-processed inclusion criteria
	 */
	private ArrayList<String> processedInclusionCriteria;
	/**
	 * pre-processed exclusion criteria
	 */
	private ArrayList<String> processedExclusionCriteria;
	/**
	 * minimum and maximum age of patients
	 */
	private MinMaxAge minMaxAge;
	/**
	 * gender of patients
	 */
	private Gender gender;
	
	/**
	 * constructor for creating data storage
	 * of pre-processed eligibility criteria
	 * @param processedInclusionCriteria pre-processed inclusion criteria
	 * @param processedExclusionCriteria pre-processed exclusion criteria
	 * @param minMaxAge minimum and maximum age of patients
	 * @param gender gender of patients
	 */
	public ProcessedCriteria(ArrayList<String> processedInclusionCriteria, ArrayList<String> processedExclusionCriteria, MinMaxAge minMaxAge, Gender gender){
		this.processedInclusionCriteria = processedInclusionCriteria;
		this.processedExclusionCriteria = processedExclusionCriteria;
		this.setMinMaxAge(minMaxAge);
		this.gender = gender;
	}
	
	/**
	 * gets pre-processed inclusion criteria
	 * @return pre-processed inclusion criteria
	 */
	public ArrayList<String> getProcessedInclusionCriteria() {
		return processedInclusionCriteria;
	}
	
	/**
	 * sets pre-processed inclusion criteria
	 * @param processedInclusionCriteria pre-processed inclusion criteria
	 */
	public void setProcessedInclusionCriteria(ArrayList<String> processedInclusionCriteria) {
		this.processedInclusionCriteria = processedInclusionCriteria;
	}
	
	/**
	 * gets pre-processed exclusion criteria
	 * @return pre-processed exclusion criteria
	 */
	public ArrayList<String> getProcessedExclusionCriteria() {
		return processedExclusionCriteria;
	}
	
	/**
	 * sets pre-processed exclusion criteria
	 * @param processedExclusionCriteria pre-processed exclusion criteria
	 */
	public void setProcessedExclusionCriteria(ArrayList<String> processedExclusionCriteria) {
		this.processedExclusionCriteria = processedExclusionCriteria;
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
	
	
	
}
