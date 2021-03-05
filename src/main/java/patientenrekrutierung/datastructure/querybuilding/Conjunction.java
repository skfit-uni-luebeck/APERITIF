package patientenrekrutierung.datastructure.querybuilding;

/**
 * class for modelling conjunctions such 
 * as "and" or "or" of an eligibilty criteria
 * @author Alexandra Banach
 *
 */
public class Conjunction {
	/**
	 * type of conjunction
	 */
	private ConjunctionType conjunctionType;
	/**
	 * position of conjunction in text from which it was extracted
	 */
	private int position;
	
	/**
	 * constructor to create conjunctions
	 * @param conjunctionType type of conjunction
	 * @param position position of conjunction in text from which it was extracted
	 */
	public Conjunction(ConjunctionType conjunctionType, int position){
		this.conjunctionType = conjunctionType;
		this.position = position;
	}

	/**
	 * gets position of conjunction in text from which it was extracted
	 * @return position of conjunction in text from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets position of conjunction in text from which it was extracted
	 * @param position of conjunction in text from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * gets type of conjunction
	 * @return type of conjunction
	 */
	public ConjunctionType getConjunctionType() {
		return conjunctionType;
	}

	/**
	 * sets type of conjunction
	 * @param conjunctionType type of conjunction
	 */
	public void setConjunctionType(ConjunctionType conjunctionType) {
		this.conjunctionType = conjunctionType;
	}
}
