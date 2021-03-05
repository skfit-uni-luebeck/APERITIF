package patientenrekrutierung.datastructure.labs;

/**
 * class for modelling values of laboratory results
 * @author Alexandra Banach
 *
 */
public class LabValue {
	/**
	 * laboratory value
	 */
	private String labValue;
	/**
	 * start position of laboratory value in text from which it was extracted
	 */
	private int position;
	
	/**
	 * constructor to create a laboratory value object
	 * @param labValue laboratory value
	 * @param position start position of laboratory value in text from which it was extracted
	 */
	public LabValue(String labValue, int position){
		this.setLabValue(labValue);
		this.setPosition(position);
	}

	/**
	 * gets a laboratory value
	 * @return laboratory value
	 */
	public String getLabValue() {
		return labValue;
	}

	/**
	 * sets a laboratory value
	 * @param labValue laboratory value
	 */
	public void setLabValue(String labValue) {
		this.labValue = labValue;
	}

	/**
	 * gets the start position of a laboratory value in the text from which it was extracted
	 * @return the start position of a laboratory value in the text from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets the start position of a laboratory value in the text from which it was extracted
	 * @param position the start position of a laboratory value in the text from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}
}
