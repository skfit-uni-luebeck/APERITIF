package patientenrekrutierung.datastructure.querybuilding;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/**
 * class for modelling a medical entity
 * e.g. diagnosis, medication, procedure, etc.
 * @author Alexandra Banach
 *
 */
public class MedicalEntity {
	/**
	 * indicates whether a medical entity is negated or not
	 */
	private boolean isNegated;
	/**
	 * name of medical entity
	 */
	private String entityName;
	/**
	 * set of subentites of a medical entity
	 */
	private HashSet<Subentity> subEntities;
	/**
	 * start position of medical entity in text from which it was extracted
	 */
	private int position;
	/**
	 * CQL query part for medical entity
	 */
	private String queryForEntity;
	/**
	 * types of medical entity
	 */
	private Set<EntityType> entityTypes;
	
	/**
	 * constructor for creating a medical entity
	 * @param isNegated indicates whether a medical entity is negated or not
	 * @param entityName name of medical entity
	 * @param subEntities subentities of medical entity
	 * @param position start position of medical entity in text from which it was extracted
	 * @param entityTypes types of medical entity
	 */
	public MedicalEntity(boolean isNegated, String entityName, HashSet<Subentity> subEntities, int position,  Set<EntityType> entityTypes){
		this.setNegated(isNegated);
		this.setEntityName(entityName);
		this.setSubEntities(subEntities);
		this.setPosition(position);
		this.setEntityTypes(entityTypes);
	}

	/**
	 * indicates whether a medical entity is negated or not
	 * @return true if medical entity is negated else false
	 */
	public boolean isNegated() {
		return isNegated;
	}

	/**
	 * sets whether a medical entity is negated or not 
	 * @param isNegated true if medical entity is negated else false
	 */
	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}

	/**
	 * gets name of a medical entity
	 * @return name of medical entity
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * sets name of a medical entity
	 * @param entityName name of medical entity
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * gets set of subentities of a medical entity
	 * @return set of subentities
	 */
	public HashSet<Subentity> getSubEntities() {
		return subEntities;
	}

	/**
	 * sets set of subentities of a medical entity
	 * @param subEntities set of subentities
	 */
	public void setSubEntities(HashSet<Subentity> subEntities) {
		this.subEntities = subEntities;
	}

	/**
	 * gets start position of a medical entity in text from which it was extracted
	 * @return start position of a medical entity in text from which it was extracted
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * sets start position of a medical entity in text from which it was extracted
	 * @param position start position of a medical entity in text from which it was extracted
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * gets CQL query part of a medical entity
	 * @return CQL query part of a medical entity
	 */
	public String getQueryForEntity() {
		return queryForEntity;
	}

	/**
	 * sets CQL query part of a medical entity
	 * @param queryForEntity CQL query part of a medical entity
	 */
	public void setQueryForEntity(String queryForEntity) {
		this.queryForEntity = queryForEntity;
	}

	/**
	 * gets entity types of a medical entity
	 * @return entity types of a medical entity
	 */
	public Set<EntityType> getEntityTypes() {
		return entityTypes;
	}

	/**
	 * sets entity types of a medical entity
	 * @param entityTypes
	 */
	public void setEntityTypes(Set<EntityType> entityTypes) {
		this.entityTypes = entityTypes;
	}
	
	/**
	 * two medical entities are equal if they have the same name
	 */
	 @Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof MedicalEntity))
				return false;
			MedicalEntity entity = (MedicalEntity) o;
			return Objects.equals(getEntityName(), entity.getEntityName());
		}

		@Override
		public int hashCode() {
			return Objects.hash(getEntityName());
		}
	
}
