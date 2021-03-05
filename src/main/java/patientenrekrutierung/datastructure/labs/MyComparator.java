package patientenrekrutierung.datastructure.labs;

/**
 * class for modelling a comparator of a laboratory entity
 * @author Alexandra Banach
 *
 */
public class MyComparator {
	/**
	 * type of comparator as defined by enumeration
	 */
	private ComparatorType comparatorType;
	/**
	 * position of comparator in text from which it was extracted
	 */
	private int position;
	
	/**
	 * constructor to create a comparator of a laboratory entity
	 * @param comparatorType as defined by enumeration
	 * @param position of comparator in text from which it was extracted
	 */
	public MyComparator(ComparatorType comparatorType, int position){
		this.setComparatorType(comparatorType);
		this.setPosition(position);
	}

	/**
	 * gets the type of a comparator
	 * @return type of a comparator
	 */
	public ComparatorType getComparatorType() {
		return comparatorType;
	}

	/**
	 * sets the type of a comparator
	 * @param comparatorType type of a comparator
	 */
	public void setComparatorType(ComparatorType comparatorType) {
		this.comparatorType = comparatorType;
	}

	/**
	 * gets the position of a comparator in the text from which it was extracted
	 * @return start position of a comparator in the text from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets the position of a comparator in the text from which it was extracted
	 * @param position start position of a comparator in the text from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
