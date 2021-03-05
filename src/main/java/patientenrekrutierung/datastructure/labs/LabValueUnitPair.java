package patientenrekrutierung.datastructure.labs;

/**
 * class for modelling laboratory objects which consist of
 * a laboratory value and a corresponding unit
 * @author Alexandra Banach
 *
 */
public class LabValueUnitPair {
	/**
	 * laboratory value
	 */
	private LabValue labValue;
	/** 
	 * laboratory unit
	 */
	private LabUnit labUnit;
	
	/**
	 * constructor to create a LabValueUnitPair
	 * for modelling laboratory results. It consists of
	 * a laboratory value and a corresponding unit.
	 * @param labValue value of laboratory result
	 * @param labUnit unit of a laboratory result
	 */
	public LabValueUnitPair(LabValue labValue, LabUnit labUnit){
		this.labValue = labValue;
		this.labUnit = labUnit;
	}

	/**
	 * gets the value of a laboratory result
	 * @return laboratory value
	 */
	public LabValue getLabValue() {
		return labValue;
	}

	/**
	 * sets the value of a laboratory result
	 * @param labValue
	 */
	public void setLabValue(LabValue labValue) {
		this.labValue = labValue;
	}

	/**
	 * gets the unit of a laboratory result
	 * @return laboratory unit
	 */
	public LabUnit getLabUnit() {
		return labUnit;
	}

	/**
	 * sets the unit of a laboratory result
	 * @param labUnit laboratory unit
	 */
	public void setLabUnit(LabUnit labUnit) {
		this.labUnit = labUnit;
	}
}
