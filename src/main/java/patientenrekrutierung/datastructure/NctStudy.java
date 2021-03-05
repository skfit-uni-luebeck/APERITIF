package patientenrekrutierung.datastructure;

/**
 * class for modelling data of a study which is
 * registered in ClinicalTrials.gov
 * @author Alexandra Banach
 *
 */
public class NctStudy {
	/**
	 * eligibility criteria
	 */
	private String eligibilityCriteria;
	/**
	 * gender of patients
	 */
	private String gender;
	/**
	 * minimum age of patients
	 */
	private String minimumAge;
	/**
	 * maximum age of patients
	 */
	private String maximumAge;
	
	/**
	 * constructor for creating a study which is
	 * registered in ClinicalTrials.gov
	 * @param eligibilityCriteria eligibility criteria of study
	 * @param gender of patients
	 * @param minimumAge minimum age of patients
	 * @param maximumAge maximum age of patients
	 */
	public NctStudy(String eligibilityCriteria, String gender, String minimumAge, String maximumAge){
		this.eligibilityCriteria = eligibilityCriteria;
		this.gender = gender;
		this.minimumAge = minimumAge;
		this.maximumAge = maximumAge;
	}

	/**
	 * gets eligibility criteria
	 * @return eligibility criteria
	 */
	public String getEligibilityCriteria() {
		return eligibilityCriteria;
	}

	/**
	 * sets eligibility criteria
	 * @param eligibilityCriteria 
	 */
	public void setEligibilityCriteria(String eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
	}

	/**
	 * gets gender of patients
	 * @return gender of patients
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * sets gender of patients
	 * @param gender of patients
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * gets minimum age of patients
	 * @return minimum age of patients
	 */
	public String getMinimumAge() {
		return minimumAge;
	}

	/**
	 * sets minimum age of patients
	 * @param minimumAge minimum age of patients
	 */
	public void setMinimumAge(String minimumAge) {
		this.minimumAge = minimumAge;
	}

	/**
	 * gets maximum age of patients
	 * @return maximum age of patients
	 */
	public String getMaximumAge() {
		return maximumAge;
	}

	/**
	 * sets maximum age of patients
	 * @param maximumAge maximum age of patients
	 */
	public void setMaximumAge(String maximumAge) {
		this.maximumAge = maximumAge;
	}
}
