package patientenrekrutierung.datastructure.querybuilding;

import java.util.ArrayList;

import patientenrekrutierung.datastructure.EntityCollection;

/**
 * class for modelling an eligibility criteria
 * @author Alexandra Banach
 *
 */
public class Criteria {
	/**
	 * collection of all entities of a criteria
	 */
	private EntityCollection entities;
	/**
	 * list of conjunctions of an eligibility criteria
	 */
	private ArrayList<Conjunction> conjunctions;
	/**
	 * text of pre-processed eligibility criterion
	 */
	private String processedCriteria;
	
	/**
	 * constructor for creating an eligibility criteria
	 * @param entities collection of all medical entities of an eligibility criterion
	 * @param conjunctions conjunction of an eligibility criterion
	 * @param processedCriteria text of pre-processed eligibility criterion
	 */
	public Criteria(EntityCollection entities, ArrayList<Conjunction> conjunctions, String processedCriteria){
		this.entities = entities;
		this.conjunctions = conjunctions;
		this.processedCriteria = processedCriteria;
	}
	
	/**
	 * gets collection of all medical entities of an eligibility criterion
	 * @return collection of all medical entities of an eligibility criterion
	 */
	public EntityCollection getEntityCollection() {
		return entities;
	}
	
	/**
	 * sets collection of all medical entities of an eligibility criterion
	 * @param entities collection of all medical entities of an eligibility criterion
	 */
	public void setEntitiyCollection(EntityCollection entities) {
		this.entities = entities;
	}
	
	/**
	 * gets conjunctions of an eligibility criterion
	 * @return conjunctions of an eligibility criterion
	 */
	public ArrayList<Conjunction> getConjunctions() {
		return conjunctions;
	}
	
	/**
	 * sets conjunctions of an eligibility criterion
	 * @param conjunctions of an eligibility criterion
	 */
	public void setConjunctions(ArrayList<Conjunction> conjunctions) {
		this.conjunctions = conjunctions;
	}

	/**
	 * gets text of pre-processed eligibility criterion
	 * @return text of pre-processed eligibility criterion
	 */
	public String getProcessedCriteria() {
		return processedCriteria;
	}

	/**
	 * sets text of pre-processed eligibility criterion
	 * @param processedCriteria text of pre-processed eligibility criterion
	 */
	public void setProcessedCriteria(String processedCriteria) {
		this.processedCriteria = processedCriteria;
	}
}
