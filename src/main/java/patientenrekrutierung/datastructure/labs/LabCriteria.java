package patientenrekrutierung.datastructure.labs;

import java.util.ArrayList;

import patientenrekrutierung.datastructure.querybuilding.Conjunction;

/**
 * class for modelling eligibility criteria which contain
 * laboratory results
 * @author Alexandra Banach
 *
 */
public class LabCriteria {
	/**
	 * list of laboratory entities
	 */
	private ArrayList<LabEntity> labEntities;
	/**
	 * list of conjunctions of an eligibility criterion
	 */
	private ArrayList<Conjunction> conjunctions;
	
	/**
	 * contstructor to create laboratory criteria
	 * @param labEntities list of laboratory entities
	 * @param conjunctions list of conjunctions
	 */
	public LabCriteria(ArrayList<LabEntity> labEntities, ArrayList<Conjunction> conjunctions){
		this.labEntities = labEntities;
		this.conjunctions = conjunctions;
	}

	/**
	 * gets list of laboratory entities
	 * @return list of laboratory entities
	 */
	public ArrayList<LabEntity> getLabEntities() {
		return labEntities;
	}

	/**
	 * sets list of laboratory entities
	 * @param labEntities list of laboratory entities
	 */
	public void setLabEntities(ArrayList<LabEntity> labEntities) {
		this.labEntities = labEntities;
	}

	/**
	 * gets list of conjunctions
	 * @return list of conjunctions
	 */
	public ArrayList<Conjunction> getConjunctions() {
		return conjunctions;
	}

	/**
	 * sets list of conjunctions
	 * @param conjunctions list of conjunctions
	 */
	public void setConjunctions(ArrayList<Conjunction> conjunctions) {
		this.conjunctions = conjunctions;
	}
	
}
