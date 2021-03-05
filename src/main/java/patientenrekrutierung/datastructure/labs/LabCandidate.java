package patientenrekrutierung.datastructure.labs;

import java.util.HashSet;

import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for modelling a potential laboratory entity
 * which consists of the entity and its codes and the comparator
 * @author Alexandra Banach
 *
 */
public class LabCandidate {
	/**
	 * name of potential laboratory entity
	 */
	private String entityName;
	/**
	 * indicated whether the entity is negated or not
	 */
	private boolean isNegated;
	/**
	 * position of entity in String from which it was extracted
	 */
	private int position;
	/**
	 * list of SNOMED CT codes for entity
	 */
	private HashSet<Subentity> snomedCodes;
	/**
	 * list of LOINC codes for entity
	 */
	private HashSet<Subentity> loincCodes;
	/**
	 * comparator for potential laboratory entity
	 */
	private MyComparator comparator;
	
	/**
	 * constructor to create a lab candidate which is
	 * a potential laboratory entity 
	 * @param entityName name of potential laboratory entity
	 * @param isNegated is entity negated or not
	 * @param position position of potential laboratory entity in String from which it was extracted
	 * @param snomedCodes list of extracted SNOMED CT codes for entity
	 * @param loincCodes list of extracted LOINC codes for entity 
	 */
	public LabCandidate(String entityName, boolean isNegated, int position, HashSet<Subentity> snomedCodes, HashSet<Subentity> loincCodes){
		this.entityName = entityName;
		this.isNegated = isNegated;
		this.position = position;
		this.snomedCodes = snomedCodes;
		this.loincCodes = loincCodes;
	}

	/**
	 * gets the name of the potential laboratory entity
	 * @return name of lab candidate entity
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * sets the name of the potential laboratory entity
	 * @param entityName name of lab candidate entity
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * indicates if lab candidate entity is negated or not
	 * @return true if lab candidate is a negated entity in text, else false
	 */
	public boolean isNegated() {
		return isNegated;
	}

	/**
	 * sets if lab candidate entity is negated or not
	 * @param isNegated true if lab candidate is a negated entity in text, else false
	 */
	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}

	/**
	 * gets the position of a lab candidate entity in a text
	 * @return position start position of lab candidate entity in String from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets the position of a lab candidate entity in a text
	 * @param position start position of lab candidate entity in String from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * gets the list of SNOMED CT codes of a lab candidate entity
	 * @return list of SNOMED CT codes of a lab candidate entity
	 */
	public HashSet<Subentity> getSnomedCodes() {
		return snomedCodes;
	}

	/**
	 * sets the list of SNOMED CT codes of a lab candidate entity
	 * @param snomedCodes list of SNOMED CT codes of a lab candidate entity
	 */
	public void setSnomedCodes(HashSet<Subentity> snomedCodes) {
		this.snomedCodes = snomedCodes;
	}

	/**
	 * gets the list of LOINC codes of a lab candidate entity
	 * @return list of LOINC codes of a lab candidate entity
	 */
	public HashSet<Subentity> getLoincCodes() {
		return loincCodes;
	}

	/**
	 * sets the list of LOINC codes of a lab candidate entity
	 * @param loincCodes list of LOINC codes of a lab candidate entity
	 */
	public void setLoincCodes(HashSet<Subentity> loincCodes) {
		this.loincCodes = loincCodes;
	}

	/**
	 * gets the comparator of a lab candidate entity
	 * @return comparator of lab candidate entity
	 */
	public MyComparator getComparator() {
		return comparator;
	}

	/**
	 * sets the comparator of lab candidate entity
	 * @param comparator of lab candidate entity
	 */
	public void setComparator(MyComparator comparator) {
		this.comparator = comparator;
	}
	
}
