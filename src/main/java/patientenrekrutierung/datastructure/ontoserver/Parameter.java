
package patientenrekrutierung.datastructure.ontoserver;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * class for modelling a parameter
 * @author Alexandra Banach
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "valueUri",
    "valueCode",
    "valueInteger",
    "valueString"
})
public class Parameter {
	/**
	 * name
	 */
    @JsonProperty("name")
    private String name;
    /**
     * value URI
     */
    @JsonProperty("valueUri")
    private String valueUri;
    /**
     * value code
     */
    @JsonProperty("valueCode")
    private String valueCode;
    /**
     * value integer
     */
    @JsonProperty("valueInteger")
    private Integer valueInteger;
    /**
     * value string
     */
    @JsonProperty("valueString")
    private String valueString;
    /**
     * additional properties
     */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * gets name
     * @return name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets value URI
     * @return value URI
     */
    @JsonProperty("valueUri")
    public String getValueUri() {
        return valueUri;
    }

    /**
     * sets value URI
     * @param valueUri
     */
    @JsonProperty("valueUri")
    public void setValueUri(String valueUri) {
        this.valueUri = valueUri;
    }

    /**
     * gets value code
     * @return value code
     */
    @JsonProperty("valueCode")
    public String getValueCode() {
        return valueCode;
    }

    /**
     * sets value code
     * @param valueCode
     */
    @JsonProperty("valueCode")
    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    /**
     * gets value integer
     * @return value integer
     */
    @JsonProperty("valueInteger")
    public Integer getValueInteger() {
        return valueInteger;
    }

    /**
     * sets value integer
     * @param valueInteger
     */
    @JsonProperty("valueInteger")
    public void setValueInteger(Integer valueInteger) {
        this.valueInteger = valueInteger;
    }

    /**
     * gets value string
     * @return value string
     */
    @JsonProperty("valueString")
    public String getValueString() {
        return valueString;
    }

    /**
     * sets value string
     * @param valueString
     */
    @JsonProperty("valueString")
    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    /**
     * gets additional properties
     * @return additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * sets additional property
     * @param name
     * @param value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
