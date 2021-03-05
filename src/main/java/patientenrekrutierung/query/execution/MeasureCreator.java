package patientenrekrutierung.query.execution;

import java.util.ArrayList;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Expression;
import org.hl7.fhir.r4.model.Measure;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Measure.MeasureGroupComponent;

/**
* class to create a FHIR resource measure
* which is needed for CQL query execution
* @author Alexandra Banach
*
*/
public class MeasureCreator {
	/**
	 * method to create a FHIR resource measure
	 * which is needed for CQL query execution
	 * @param measureId id of measure
	 * @param measureUrl url of measure
	 * @return FHIR resource Measure
	 */
	public Measure createMeasure(String measureId, String measureUrl){
		Measure measure = new Measure();
		// criteria
		Expression criteria = new Expression();
		criteria.setLanguage("text/cql");
		criteria.setExpression("InInitialPopulation");
		// code
		Coding code = new Coding();
		code.setSystem("http://terminology.hl7.org/CodeSystem/measure-population");
		code.setCode("initial-population");
		CodeableConcept codConc = new CodeableConcept();
		codConc.addCoding(code);
		// population and group
		ArrayList<MeasureGroupComponent> group = new ArrayList<MeasureGroupComponent>();
		MeasureGroupComponent measureGroupComponent = new MeasureGroupComponent();
		measureGroupComponent.addPopulation()
			.setCriteria(criteria)
			.setCode(codConc);
		group.add(measureGroupComponent);
		measure.setGroup(group);
	
		
		// meta
		Meta meta = new Meta();
		meta.setVersionId("12018");
		measure.setMeta(meta);
		// subject codeable concept
		Coding subCodConc = new Coding();
		subCodConc.setCode("Patient");
		subCodConc.setSystem("http://hl7.org/fhir/resource-types");
		ArrayList<Coding> codinglist = new ArrayList<Coding>();
		codinglist.add(subCodConc);
		measure.getSubjectCodeableConcept().setCoding(codinglist);
		// status
		measure.setStatus(Enumerations.PublicationStatus.ACTIVE);
		// id
		measure.setId(measureId);
		// url
		measure.setUrl(measureUrl);
		// scoring
		Coding coding = new Coding();
		coding.setCode("cohort");
		coding.setSystem("http://terminology.hl7.org/CodeSystem/measure-scoring");
		CodeableConcept codeableConcept = new CodeableConcept();
		codeableConcept.addCoding(coding);
		measure.setScoring(codeableConcept);
		
		return measure;
	}
}
