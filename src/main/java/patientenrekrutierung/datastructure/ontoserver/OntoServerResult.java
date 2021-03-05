
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
 * class for modelling the result of an Ontoserver REST request
 * @author Alexandra Banach
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resourceType",
    "language",
    "url",
    "name",
    "status",
    "experimental",
    "copyright",
    "expansion"
})
public class OntoServerResult {

	/**
	 * resource type
	 */
    @JsonProperty("resourceType")
    private String resourceType;
    /**
     * language
     */
    @JsonProperty("language")
    private String language;
    /**
     * url
     */
    @JsonProperty("url")
    private String url;
    /**
     * name
     */
    @JsonProperty("name")
    private String name;
    /**
     * status
     */
    @JsonProperty("status")
    private String status;
    /**
     * experimental
     */
    @JsonProperty("experimental")
    private Boolean experimental;
    /**
     * copyright
     */
    @JsonProperty("copyright")
    private String copyright;
    /**
     * expansion
     */
    @JsonProperty("expansion")
    private Expansion expansion;
    /**
     * additional properties
     */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * get resource type
     * @return resource type
     */
    @JsonProperty("resourceType")
    public String getResourceType() {
        return resourceType;
    }

    /**
     * set resource type
     * @param resourceType
     */
    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * get language
     * @return language
     */
    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    /**
     * set language
     * @param language
     */
    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * get url
     * @return url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * set url
     * @param url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * get name
     * @return name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get status
     * @return status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * set status
     * @param status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * get experimental
     * @return experimental
     */
    @JsonProperty("experimental")
    public Boolean getExperimental() {
        return experimental;
    }

    /**
     * set experimental
     * @param experimental
     */
    @JsonProperty("experimental")
    public void setExperimental(Boolean experimental) {
        this.experimental = experimental;
    }

    /**
     * get copyright
     * @return copyright
     */
    @JsonProperty("copyright")
    public String getCopyright() {
        return copyright;
    }

    /**
     * set copyright
     * @param copyright
     */
    @JsonProperty("copyright")
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * get expansion
     * @return expansion
     */
    @JsonProperty("expansion")
    public Expansion getExpansion() {
        return expansion;
    }

    /**
     * set expansion
     * @param expansion
     */
    @JsonProperty("expansion")
    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }

    /**
     * get additional properties
     * @return additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * set additional properties
     * @param name
     * @param value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
