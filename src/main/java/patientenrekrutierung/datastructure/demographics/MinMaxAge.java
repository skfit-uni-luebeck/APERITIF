package patientenrekrutierung.datastructure.demographics;

/**
 * class to model minimum and maximum age
 * of a person
 * @author Alexandra Banach
 *
 */
public class MinMaxAge {
	/**
	 * minimum age of a patient
	 */
	private Age minAge;
	/**
	 * maximum age of a patient
	 */
	private Age maxAge;
	
	/**
	 * constructor to create an object to
	 * model minimum and maximum age of a patient
	 * @param minAge minimum age of a patient
	 * @param maxAge maximum age of a patient
	 */
	public MinMaxAge(Age minAge, Age maxAge){
		this.setMinAge(minAge);
		this.setMaxAge(maxAge);
	}
	
	/**
	 * gets the minimum age
	 * @return minimum age
	 */
	public Age getMinAge() {
		return minAge;
	}

	/**
	 * sets the minimum age
	 * @param minAge minimum age of a patient
	 */
	public void setMinAge(Age minAge) {
		this.minAge = minAge;
	}

	/**
	 * gets the maximum age 
	 * @return maximum age of a patient
	 */
	public Age getMaxAge() {
		return maxAge;
	}

	/**
	 * sets the maximum age
	 * @param maxAge maximum age of a patient
	 */
	public void setMaxAge(Age maxAge) {
		this.maxAge = maxAge;
	}
	
	
}
