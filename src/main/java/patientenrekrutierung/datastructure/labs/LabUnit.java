package patientenrekrutierung.datastructure.labs;

import java.util.Objects;


/**
 * class for modelling laboratory units
 * @author Alexandra Banach
 *
 */
public class LabUnit {
	/**
	 * laboratory unit
	 */
	private String labUnit;
	/**
	 * start position of a laboratory unit in the text from which it was extracted
	 */
	private int position;
	
	/**
	 * constructor to create a laboratory unit
	 * @param labUnit laboratory unit
	 * @param position start position of a laboratory unit in the text from which it was extracted
	 */
	public LabUnit(String labUnit, int position){
		this.setLabUnit(labUnit);
		this.setPosition(position);
	}

	/**
	 * gets a laboratory unit
	 * @return laboratory unit
	 */
	public String getLabUnit() {
		return labUnit;
	}

	/**
	 * sets a laboratory unit
	 * @param labUnit laboratory unit
	 */
	public void setLabUnit(String labUnit) {
		this.labUnit = labUnit;
	}

	/**
	 * gets the start position of a laboratory unit in the text from which it was extracted
	 * @return start position of a laboratory unit in the text from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets the start position of a laboratory unit in the text from which it was extracted
	 * @param position start position of a laboratory unit in the text from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * laboratory units are equal if they have the same position in a text
	 */
	 @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof LabUnit))
			return false;
		LabUnit labunit = (LabUnit) o;
		return Objects.equals(getPosition(), labunit.getPosition());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPosition());
	}

}
