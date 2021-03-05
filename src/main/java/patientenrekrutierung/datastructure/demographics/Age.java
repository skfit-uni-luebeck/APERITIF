package patientenrekrutierung.datastructure.demographics;

/**
 * class which models a patient' s age
 * @author Alexandra Banach
 *
 */
public class Age {
	/**
	 * value of an age
	 */
	private AgeValue ageValue;
	/**
	 * time unit of an age
	 */
	private AgeUnit ageUnit;
	
	/**
	 * constructor for creating a new age
	 * consisting of an value and a unit
	 * @param ageValue value of an age
	 * @param ageUnit time unit of an age
	 */
	public Age(AgeValue ageValue, AgeUnit ageUnit){
		this.setAgeValue(ageValue);
		this.setAgeUnit(ageUnit);
	}

	/**
	 * gets the age value of an age
	 * @return value of an age
	 */
	public AgeValue getAgeValue() {
		return ageValue;
	}
	
	/**
	 * sets the value of an age
	 * @param ageValue value of an age
	 */
	public void setAgeValue(AgeValue ageValue) {
		this.ageValue = ageValue;
	}

	/**
	 * gets the unit of an age
	 * @return unit of an age
	 */
	public AgeUnit getAgeUnit() {
		return ageUnit;
	}
	
	/**
	 * sets the unit of an age
	 * @param ageUnit unit of an age
	 */
	public void setAgeUnit(AgeUnit ageUnit) {
		this.ageUnit = ageUnit;
	}
}
