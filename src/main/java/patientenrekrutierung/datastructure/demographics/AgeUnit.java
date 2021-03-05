package patientenrekrutierung.datastructure.demographics;

import java.util.Objects;


/**
 * class modelling the unit of an age
 * @author Alexandra Banach
 *
 */
public class AgeUnit {
	/**
	 * unit of an age, represented by thhe corresponding enum
	 */
	private AgeUnitsEnum ageUnit;
	/**
	 * position of age in the String from which it was extracted
	 */
	private int position;
	
	/**
	 * contructor to create an AgeUnit
	 * @param ageUnit time unit of age, represented by enumeration
	 * @param position position of age in the String from which it was extracted
	 */
	public AgeUnit(AgeUnitsEnum ageUnit, int position){
		this.setAgeUnit(ageUnit);
		this.setPosition(position);
	}

	/**
	 * gets an age unit
	 * @return age unit as defined by enumeration
	 */
	public AgeUnitsEnum getAgeUnit() {
		return ageUnit;
	}

	/**
	 * sets an age unit
	 * @param ageUnit as defined by enumeration
	 */
	public void setAgeUnit(AgeUnitsEnum ageUnit) {
		this.ageUnit = ageUnit;
	}

	/**
	 * gets the position of an age unit in the String from which it was extracted
	 * @return position of age unit in the String from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets the position of an age unit in the String from which it was extracted
	 * @param position of age unit in the String from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * Two age units are equal if they have
	 * the same position in a String
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AgeUnit))
			return false;
		AgeUnit ageunit = (AgeUnit) o;
		return Objects.equals(getPosition(), ageunit.getPosition());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPosition());
	}
}
