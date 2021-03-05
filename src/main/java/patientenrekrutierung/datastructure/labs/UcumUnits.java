package patientenrekrutierung.datastructure.labs;

import java.util.ArrayList;

/**
 * class for modelling UCUM units
 * (needed for representing laboratory results)
 * @author Alexandra Banach
 *
 */
public class UcumUnits {
	/**
	 * the original UCUM Code, e.g. mm[Hg]
	 */
	private ArrayList<String> originalCodes;
	/**
	 * synonyms of UCUM codes, e.g. mmHg
	 */
	private ArrayList<String> synonymCodes;
	
	/**
	 * constructor to create a UCUM unit object
	 * @param originalCodes list of original UCUM codes frequently used
	 * @param synonymCodes list of synonyms of UCUM codes
	 */
	public UcumUnits(ArrayList<String> originalCodes, ArrayList<String> synonymCodes){
		this.setOriginalCodes(originalCodes);
		this.setSynonymCodes(synonymCodes);
	}

	/**
	 * get list of original UCUM codes
	 * @return list of original UCUM codes
	 */
	public ArrayList<String> getOriginalCodes() {
		return originalCodes;
	}

	/**
	 * sets original UCUM codes
	 * @param originalCodes list of original UCUM codes
	 */
	public void setOriginalCodes(ArrayList<String> originalCodes) {
		this.originalCodes = originalCodes;
	}

	/**
	 * gets a list of synonyms of UCUM codes
	 * @return list of synonyms of  UCUM codes
	 */
	public ArrayList<String> getSynonymCodes() {
		return synonymCodes;
	}

	/**
	 * sets a list of synonyms of UCUM codes
	 * @param synonymCodes list of synonyms of  UCUM codes
	 */
	public void setSynonymCodes(ArrayList<String> synonymCodes) {
		this.synonymCodes = synonymCodes;
	}
}
