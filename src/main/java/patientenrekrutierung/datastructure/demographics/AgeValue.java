package patientenrekrutierung.datastructure.demographics;

/**
 * class which models the value of an age
 * @author Alexandra Banach
 *
 */
public class AgeValue {
	/**
	 * value of an age
	 */
	private String ageValue;
	/**
	 * position of the age value in String from which it was extracted
	 */
	private int position;
	
	/**
	 * constructor to create an AgeValue
	 * @param ageValue value of age
	 * @param position position of the age value in String from which it was extracted
	 */
	public AgeValue(String ageValue, int position){
		this.ageValue = ageValue;
		this.position = position;
	}

	/**
	 * gets an age value
	 * @return age value value of an age
	 */
	public String getAgeValue() {
		return ageValue;
	}

	/**
	 * sets an age value
	 * @param ageValue value of an age
	 */
	public void setAgeValue(String ageValue) {
		this.ageValue = ageValue;
	}

	/**
	 * gets the position of an age value in String from which it was extracted
	 * @return position of the age value in String from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets the position of an age value in String from which it was extracted
	 * @param position of the age value in String from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}
}
