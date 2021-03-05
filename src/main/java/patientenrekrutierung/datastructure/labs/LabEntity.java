package patientenrekrutierung.datastructure.labs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for modelling laboratory entitites
 * @author Alexandra Banach
 *
 */
public class LabEntity {
	/**
	 * name of laboratory entity
	 */
	private String labEntityName;
	/**
	 * indicated if laboratory entity is negated or not
	 */
	private boolean isNegated;
	/**
	 * start position of laboratory entity in text from which it was extracted
	 */
	private int position; 
	/**
	 * type of comparator
	 */
	private ComparatorType comparator;
	/**
	 * list of pairs of laboratory values and corresponding units
	 */
	private ArrayList<LabValueUnitPair> valueUnit;
	/**
	 * list of SNOMED CT codes for laboratory entity
	 */
	private HashSet<Subentity> snomedCodes;
	/**
	 * list of LOINC codes for laboratory entity
	 */
	private HashSet<Subentity> loincCodes;

	/**
	 * constructor to create a laboratory entity
	 * @param labEntityName name of laboratory entity
	 * @param isNegated indicated if laboratory entity is negated or not
	 * @param position start position of laboratory entity in text from which it was extracted
	 * @param comparator comparator of laboratory entity
	 * @param valueUnit laboratory values and units
	 * @param snomedCodes list of SNOMED CT codes for laboratory entity
	 * @param loincCodes list of LOINC codes for laboratory entity
	 */
	public LabEntity(String labEntityName, boolean isNegated, int position, ComparatorType comparator, ArrayList<LabValueUnitPair> valueUnit,
			HashSet<Subentity> snomedCodes, HashSet<Subentity> loincCodes) {
		this.setLabEntityName(labEntityName);
		this.setNegated(isNegated);
		this.setPosition(position);
		this.setComparator(comparator);
		this.setValueUnit(valueUnit);
		this.setSnomedCodes(snomedCodes);
		this.setLoincCodes(loincCodes);
	}

	/**
	 * gets the name of a laboratory entity
	 * @return name of laboratory entity
	 */
	public String getLabEntityName() {
		return labEntityName;
	}

	/**
	 * sets the name of a laboratory entity
	 * @param labEntityName name of laboratory entity
	 */
	public void setLabEntityName(String labEntityName) {
		this.labEntityName = labEntityName;
	}

	/**
	 * indicates if laboratory entity is negated or not
	 * @return true if laboratory entity is negated else false
	 */
	public boolean isNegated() {
		return isNegated;
	}

	/**
	 * sets if laboratory entity is negated or not
	 * @param isNegated
	 */
	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}

	/**
	 * gets the comparator of a laboratory entity
	 * @return comparator of laboratory entity
	 */
	public ComparatorType getComparator() {
		return comparator;
	}

	/**
	 * sets the comparator of a laboratory entity
	 * @param comparator of a laboratory entity
	 */
	public void setComparator(ComparatorType comparator) {
		this.comparator = comparator;
	}

	/**
	 * gets the list of SNOMED CT codes of a laboratory entity
	 * @return list of SNOMED CT codes of a laboratory entity
	 */
	public HashSet<Subentity> getSnomedCodes() {
		return snomedCodes;
	}

	/**
	 * sets the list of SNOMED CT codes of a laboratory entity
	 * @param snomedCodes list of SNOMED CT codes of a laboratory entity
	 */
	public void setSnomedCodes(HashSet<Subentity> snomedCodes) {
		this.snomedCodes = snomedCodes;
	}

	/**
	 * gets the start position of a laboratory entity in the text from which it was extracted
	 * @return start position of a laboratory entity in the text from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets start position of a laboratory entity in the text from which it was extracted
	 * @param position start position of a laboratory entity in the text from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * gets LOINC codes of a laboratory entity
	 * @return LOINC codes of a laboratory entity
	 */
	public HashSet<Subentity> getLoincCodes() {
		return loincCodes;
	}

	/**
	 * sets LOINC codes of a laboratory entity
	 * @param loincCodes LOINC codes of a laboratory entity
	 */
	public void setLoincCodes(HashSet<Subentity> loincCodes) {
		this.loincCodes = loincCodes;
	}

	/**
	 * gets list of pairs of laboratory values and units
	 * @return list of pairs of laboratory values and units
	 */
	public ArrayList<LabValueUnitPair> getValueUnit() {
		return valueUnit;
	}

	/**
	 * sets list of pairs of laboratory values and units
	 * @param valueUnit list of pairs of laboratory values and units
	 */
	public void setValueUnit(ArrayList<LabValueUnitPair> valueUnit) {
		this.valueUnit = valueUnit;
	}
	
	/**
	 * laboratory entities are equal if they have the same name
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof LabEntity))
			return false;
		LabEntity entity = (LabEntity) o;
		return Objects.equals(getLabEntityName(), entity.getLabEntityName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getLabEntityName());
	}
}

