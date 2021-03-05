
package patientenrekrutierung.datastructure.nct;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "StudyFieldsResponse"
})

/**
 * class for storing study data acquired from 
 * ClinicalTrials.gov via REST
 * @author Alexandra Banach
 *
 */
public class NCTstudy {
	/**
	 * StudyFieldResponsee
	 */
    @JsonProperty("StudyFieldsResponse")
    private StudyFieldsResponse studyFieldsResponse;
    
    /**
     * additionalProperties
     */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * gets studyFieldsResponse
     * @return studyFieldsResponse
     */
    @JsonProperty("StudyFieldsResponse")
    public StudyFieldsResponse getStudyFieldsResponse() {
        return studyFieldsResponse;
    }

    /**
     * sets studyFieldsResponse
     * @param studyFieldsResponse 
     */
    @JsonProperty("StudyFieldsResponse")
    public void setStudyFieldsResponse(StudyFieldsResponse studyFieldsResponse) {
        this.studyFieldsResponse = studyFieldsResponse;
    }

    /**
     * gets additionalProperties
     * @return additionalProperties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * sets additionalProperty
     * @param name
     * @param value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
