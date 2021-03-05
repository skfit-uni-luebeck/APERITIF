package patientenrekrutierung.datastructure;

import java.util.ArrayList;


import patientenrekrutierung.datastructure.labs.LabCriteria;
import patientenrekrutierung.datastructure.querybuilding.MedicalEntity;

/**
 * class for modelling a collection of all entities of a criterion
 * @author Alexandra Banach
 *
 */
public class EntityCollection {
	/**
	 * all medical entities of an eligibility criterion
	 */
	private ArrayList<MedicalEntity> metamapEntities;
	/**
	 * all laboratory entities of an eligibility criterion
	 */
	private LabCriteria labCriteria;
	
	/**
	 * constructor for creating an entity collection
	 * @param metamapEntities medical entities of an eligibility criterion
	 * @param labCriteria laboratory entities of an eligibility criterion
	 */
	public EntityCollection(ArrayList<MedicalEntity> metamapEntities, LabCriteria labCriteria){
		this.metamapEntities = metamapEntities;
		this.labCriteria = labCriteria;
	}

	/**
	 * gets all medical entities of an eligibility criterion
	 * @return medical entities of an eligibility criterion
	 */
	public ArrayList<MedicalEntity> getMetamapEntities() {
		return metamapEntities;
	}

	/**
	 * sets all medical entities of an eligibility criterion
	 * @param metamapEntities
	 */
	public void setMetamapEntities(ArrayList<MedicalEntity> metamapEntities) {
		this.metamapEntities = metamapEntities;
	}

	/**
	 * gets all laboratory entities of an eligibility criterion
	 * @return laboratory entities of an eligibility criterion
	 */
	public LabCriteria getLabCriteria() {
		return labCriteria;
	}

	/**
	 * sets all laboratory entities of an eligibility criterion
	 * @param labCriteria laboratory entities of an eligibility criterion
	 */
	public void setLabCriteria(LabCriteria labCriteria) {
		this.labCriteria = labCriteria;
	}
	
}
