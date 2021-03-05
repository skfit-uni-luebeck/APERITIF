package patientenrekrutierung.datastructure;

import java.util.ArrayList;

/**
 * class for modelling the collection of all
 * inclusion and exclusion criteria
 * @author Alexandra Banach
 *
 */
public class EligibilityCriteria {
	/**
	 * inclusion criteria of a study
	 */
	private ArrayList<String[]> inclusionCriteria;
	/**
	 * exclusion criteria of a study
	 */
	private ArrayList<String[]> exclusionCriteria;
	
	/**
	 * constructor for creating the collection of all inclusion and exclusion criteria
	 * @param inclusionCriteria inclusion criteria of a study
	 * @param exclusionCriteria exclusion criteria of a study
	 */
	public EligibilityCriteria(ArrayList<String[]> inclusionCriteria, ArrayList<String[]> exclusionCriteria){
		this.inclusionCriteria = inclusionCriteria;
		this.exclusionCriteria = exclusionCriteria;
	}
	
	/**
	 * gets all inclusion criteria of a study
	 * @return inclusion criteria of a study
	 */
	public ArrayList<String[]> getInclusionCriteria() {
		return inclusionCriteria;
	}
	
	/**
	 * sets all inclusion criteria of a study
	 * @param inclusionCriteria inclusion criteria of a study
	 */
	public void setInclusionCriteria(ArrayList<String[]> inclusionCriteria) {
		this.inclusionCriteria = inclusionCriteria;
	}
	
	/**
	 * gets all exclusion criteria of a study
	 * @return exclusion criteria of a study
	 */
	public ArrayList<String[]> getExclusionCriteria() {
		return exclusionCriteria;
	}
	
	/**
	 * sets all exclusion criteria of a study
	 * @param exclusionCriteria exclusion criteria of a study
	 */
	public void setExclusionCriteria(ArrayList<String[]> exclusionCriteria) {
		this.exclusionCriteria = exclusionCriteria;
	}
	
	
}
