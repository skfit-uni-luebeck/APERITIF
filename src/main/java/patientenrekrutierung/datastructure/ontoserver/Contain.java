
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
 * class for modelling Contains
 * @author Alexandra Banach
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "system",
    "code",
    "display"
})
public class Contain {
	/**
	 * system
	 */
    @JsonProperty("system")
    private String system;
    /**
     * code
     */
    @JsonProperty("code")
    private String code;
    /**
     * display
     */
    @JsonProperty("display")
    private String display;
    /**
     * additional properties
     */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * gets system
     * @return system
     */
    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    /**
     * sets system
     * @param system
     */
    @JsonProperty("system")
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * gets code
     * @return code
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * sets code
     * @param code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * gets display
     * @return display
     */
    @JsonProperty("display")
    public String getDisplay() {
        return display;
    }

    /**
     * sets display
     * @param display
     */
    @JsonProperty("display")
    public void setDisplay(String display) {
        this.display = display;
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
