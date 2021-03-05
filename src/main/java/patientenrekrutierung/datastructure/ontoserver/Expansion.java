
package patientenrekrutierung.datastructure.ontoserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * class for modelling an Expansion
 * @author Alexandra Banach
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "extension",
    "identifier",
    "timestamp",
    "total",
    "offset",
    "parameter",
    "contains"
})
public class Expansion {
	/**
	 * extension
	 */
    @JsonProperty("extension")
    private List<Extension> extension = null;
    /**
     * identifier
     */
    @JsonProperty("identifier")
    private String identifier;
    /**
     * timestamp
     */
    @JsonProperty("timestamp")
    private String timestamp;
    /**
     * total
     */
    @JsonProperty("total")
    private Integer total;
    /**
     * offset
     */
    @JsonProperty("offset")
    private Integer offset;
    /**
     * parameter
     */
    @JsonProperty("parameter")
    private List<Parameter> parameter = null;
    /**
     * contains
     */
    @JsonProperty("contains")
    private List<Contain> contains = null;
    /**
     * additional properties
     */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * gets extension
     * @return extension
     */
    @JsonProperty("extension")
    public List<Extension> getExtension() {
        return extension;
    }

    /**
     * sets extension
     * @param extension
     */
    @JsonProperty("extension")
    public void setExtension(List<Extension> extension) {
        this.extension = extension;
    }

    /**
     * get identifier
     * @return identifier
     */
    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * sets identifier
     * @param identifier
     */
    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * gets timestamp
     * @return timestamp
     */
    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * set timestamp
     * @param timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * gets total
     * @return total
     */
    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    /**
     * sets total
     * @param total
     */
    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * gets offset
     * @return offset
     */
    @JsonProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    /**
     * sets offset
     * @param offset
     */
    @JsonProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * gets parameter
     * @return parameter
     */
    @JsonProperty("parameter")
    public List<Parameter> getParameter() {
        return parameter;
    }

    /**
     * sets parameter
     * @param parameter
     */
    @JsonProperty("parameter")
    public void setParameter(List<Parameter> parameter) {
        this.parameter = parameter;
    }

    /**
     * gets contains
     * @return contains
     */
    @JsonProperty("contains")
    public List<Contain> getContains() {
        return contains;
    }

    /**
     * sets contains
     * @param contains
     */
    @JsonProperty("contains")
    public void setContains(List<Contain> contains) {
        this.contains = contains;
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
