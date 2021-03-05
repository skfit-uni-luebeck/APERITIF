
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
 * class for modelling a StudyFieldsResponse
 * @author Alexandra Banach
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "APIVrs",
    "DataVrs",
    "Expression",
    "NStudiesAvail",
    "NStudiesFound",
    "MinRank",
    "MaxRank",
    "NStudiesReturned",
    "FieldList",
    "StudyFields"
})
public class StudyFieldsResponse {
	/**
	 * APIVrs
	 */
    @JsonProperty("APIVrs")
    private String aPIVrs;
    /**
     * DataVrs
     */
    @JsonProperty("DataVrs")
    private String dataVrs;
    /**
     * expression
     */
    @JsonProperty("Expression")
    private String expression;
    /**
     * number of studies available
     */
    @JsonProperty("NStudiesAvail")
    private Integer nStudiesAvail;
    /**
     * number of studies found
     */
    @JsonProperty("NStudiesFound")
    private Integer nStudiesFound;
    /**
     * minimum rank
     */
    @JsonProperty("MinRank")
    private Integer minRank;
    /**
     * maximum rank
     */
    @JsonProperty("MaxRank")
    private Integer maxRank;
    /**
     * number of studies returned
     */
    @JsonProperty("NStudiesReturned")
    private Integer nStudiesReturned;
    /**
     * field list
     */
    @JsonProperty("FieldList")
    private List<String> fieldList = null;
    /**
     * study fields
     */
    @JsonProperty("StudyFields")
    private List<StudyField> studyFields = null;
    /**
     * additional properties
     */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * get APIVrs
     * @return APIVrs
     */
    @JsonProperty("APIVrs")
    public String getAPIVrs() {
        return aPIVrs;
    }

    /**
     * set APIVrs
     * @param aPIVrs
     */
    @JsonProperty("APIVrs")
    public void setAPIVrs(String aPIVrs) {
        this.aPIVrs = aPIVrs;
    }

    /**
     * gets DataVrs
     * @return DataVrs
     */
    @JsonProperty("DataVrs")
    public String getDataVrs() {
        return dataVrs;
    }

    /**
     * set DataVrs
     * @param dataVrs
     */
    @JsonProperty("DataVrs")
    public void setDataVrs(String dataVrs) {
        this.dataVrs = dataVrs;
    }
    
    /**
     * get expression
     * @return expression
     */
    @JsonProperty("Expression")
    public String getExpression() {
        return expression;
    }

    /**
     * set expression
     * @param expression
     */
    @JsonProperty("Expression")
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * get number of studies available
     * @return number of studies available
     */
    @JsonProperty("NStudiesAvail")
    public Integer getNStudiesAvail() {
        return nStudiesAvail;
    }

    /**
     * set number of studies available
     * @param nStudiesAvail number of studies available
     */
    @JsonProperty("NStudiesAvail")
    public void setNStudiesAvail(Integer nStudiesAvail) {
        this.nStudiesAvail = nStudiesAvail;
    }

    /**
     * get number of studies found
     * @return number of studies found
     */
    @JsonProperty("NStudiesFound")
    public Integer getNStudiesFound() {
        return nStudiesFound;
    }
    
    /**
     * set number of studies found
     * @param nStudiesFound number of studies found
     */
    @JsonProperty("NStudiesFound")
    public void setNStudiesFound(Integer nStudiesFound) {
        this.nStudiesFound = nStudiesFound;
    }

    /**
     * get minimum rank
     * @return minimum rank
     */
    @JsonProperty("MinRank")
    public Integer getMinRank() {
        return minRank;
    }

    /**
     * set minimum rank
     * @param minRank minimum rank
     */
    @JsonProperty("MinRank")
    public void setMinRank(Integer minRank) {
        this.minRank = minRank;
    }

    /**
     * get maximum rank
     * @return maximum rank
     */
    @JsonProperty("MaxRank")
    public Integer getMaxRank() {
        return maxRank;
    }

    /**
     * set maximum rank
     * @param maxRank maximum rank
     */
    @JsonProperty("MaxRank")
    public void setMaxRank(Integer maxRank) {
        this.maxRank = maxRank;
    }

    /**
     * get number of studies returned
     * @return number of studies returned
     */
    @JsonProperty("NStudiesReturned")
    public Integer getNStudiesReturned() {
        return nStudiesReturned;
    }

    /**
     * set number of studies returned
     * @param nStudiesReturned number of studies returned
     */
    @JsonProperty("NStudiesReturned")
    public void setNStudiesReturned(Integer nStudiesReturned) {
        this.nStudiesReturned = nStudiesReturned;
    }

    /**
     * get field list
     * @return field list
     */
    @JsonProperty("FieldList")
    public List<String> getFieldList() {
        return fieldList;
    }

    /**
     * set field list
     * @param fieldList field list
     */
    @JsonProperty("FieldList")
    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    /**
     * get study fields
     * @return study fields
     */
    @JsonProperty("StudyFields")
    public List<StudyField> getStudyFields() {
        return studyFields;
    }

    /**
     * set study fields
     * @param studyFields
     */
    @JsonProperty("StudyFields")
    public void setStudyFields(List<StudyField> studyFields) {
        this.studyFields = studyFields;
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
