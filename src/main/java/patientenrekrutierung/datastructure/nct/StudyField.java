
package patientenrekrutierung.datastructure.nct;

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
 * class for modelling StudyFields
 * @author Alexandra Banach
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Rank",
    "NCTId",
    "EligibilityCriteria",
    "Gender",
    "MinimumAge",
    "MaximumAge"
})
public class StudyField {

	/**
	 * rank
	 */
    @JsonProperty("Rank")
    private Integer rank;
    /**
     * nct id of trial
     */
    @JsonProperty("NCTId")
    private List<String> nCTId = null;
    /**
     * eligibility criteria of trial
     */
    @JsonProperty("EligibilityCriteria")
    private List<String> eligibilityCriteria = null;
    /**
     * gender which should be fulfilled by patients joining trial
     */
    @JsonProperty("Gender")
    private List<String> gender = null;
    /**
     * minimum age which should be fulfilled by patients joining trial
     */
    @JsonProperty("MinimumAge")
    private List<String> minimumAge = null;
    /**
     * maximum age which should be fulfilled by patients joining trial
     */
    @JsonProperty("MaximumAge")
    private List<Object> maximumAge = null;
    /**
     * additional properties
     */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * gets rank
     * @return rank
     */
    @JsonProperty("Rank")
    public Integer getRank() {
        return rank;
    }

    /**
     * sets rank
     * @param rank
     */
    @JsonProperty("Rank")
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * gets nct id of study
     * @return nct id of study
     */
    @JsonProperty("NCTId")
    public List<String> getNCTId() {
        return nCTId;
    }

    /**
     * set nct id of study
     * @param nCTId nct id of study
     */
    @JsonProperty("NCTId")
    public void setNCTId(List<String> nCTId) {
        this.nCTId = nCTId;
    }

    /**
     * get list of eligibility criteria of trial
     * @return list of eligibility criteria of trial
     */
    @JsonProperty("EligibilityCriteria")
    public List<String> getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    /**
     * sets list of eligibility criteria of trial
     * @param eligibilityCriteria list of eligibility criteria of trial
     */
    @JsonProperty("EligibilityCriteria")
    public void setEligibilityCriteria(List<String> eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    /**
     * sets gender which should be fulfilled by patients joining trial
     * @return gender which should be fulfilled by patients joining trial
     */
    @JsonProperty("Gender")
    public List<String> getGender() {
        return gender;
    }

    /**
     * sets gender which should be fulfilled by patients joining trial
     * @param gender which should be fulfilled by patients joining trial
     */
    @JsonProperty("Gender")
    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    /**
     * gets minimum age which should be fulfilled by patients joining trial
     * @return minimum age which should be fulfilled by patients joining trial
     */
    @JsonProperty("MinimumAge")
    public List<String> getMinimumAge() {
        return minimumAge;
    }

    /**
     * sets minimum age which should be fulfilled by patients joining trial
     * @param minimumAge which should be fulfilled by patients joining trial
     */
    @JsonProperty("MinimumAge")
    public void setMinimumAge(List<String> minimumAge) {
        this.minimumAge = minimumAge;
    }

    /**
     * gets maximum age which should be fulfilled by patients joining trial
     * @return maximum age which should be fulfilled by patients joining trial
     */
    @JsonProperty("MaximumAge")
    public List<Object> getMaximumAge() {
        return maximumAge;
    }

    /**
     * sets maximum age which should be fulfilled by patients joining trial
     * @param maximumAge which should be fulfilled by patients joining trial
     */
    @JsonProperty("MaximumAge")
    public void setMaximumAge(List<Object> maximumAge) {
        this.maximumAge = maximumAge;
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
     * sets additional properties
     * @param name
     * @param value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
