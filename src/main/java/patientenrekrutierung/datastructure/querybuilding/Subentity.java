package patientenrekrutierung.datastructure.querybuilding;

import java.util.Objects;

/**
 * class for modelling medical subentities
 * with codes, name and type
 * @author Alexandra Banach
 *
 */
public class Subentity {
	/**
	 * code of subentity
	 */
	private String subEntityCode;
	/**
	 * name of subentity
	 */
	private String subEntityName;
	/**
	 * type of subentity e.g. procedure or medication
	 */
	private EntityType subEntityType;
	
	/**
	 * constructor for creating subentities
	 * @param subEntityCode code of subentity
	 * @param subEntityName name of subentity
	 * @param subEntityType type of subentity
	 */
	public Subentity(String subEntityCode, String subEntityName, EntityType subEntityType){
		this.subEntityCode = subEntityCode;
		this.subEntityName = subEntityName;
		this.subEntityType = subEntityType;
	}
	
	/**
	 * gets code of a subentity
	 * @return code of subentity
	 */
	public String getSubEntityCode() {
		return subEntityCode;
	}
	
	/**
	 * sets code of subentity
	 * @param subEntityCode code of subentity
	 */
	public void setSubEntityCode(String subEntityCode) {
		this.subEntityCode = subEntityCode;
	}
	
	/**
	 * gets name of subentity
	 * @return name of subentity
	 */
	public String getSubEntityName() {
		return subEntityName;
	}
	
	/**
	 * sets name of subentity
	 * @param subEntityName name of subentity
	 */
	public void setSubEntityName(String subEntityName) {
		this.subEntityName = subEntityName;
	}

	/**
	 * gets type of subentity
	 * @return type of subentity
	 */
	public EntityType getSubentityType() {
		return subEntityType;
	}

	/**
	 * sets type of subentity
	 * @param subentityType type of subentity
	 */
	public void setSubentityType(EntityType subentityType) {
		this.subEntityType = subentityType;
	}
	
	/**
	 * two subentities are equal if they have the same code
	 */
	 @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Subentity))
			return false;
		Subentity subentity = (Subentity) o;
		return Objects.equals(getSubEntityCode(), subentity.getSubEntityCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSubEntityCode());
	}
}
