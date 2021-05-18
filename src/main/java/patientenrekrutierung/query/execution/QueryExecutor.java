package patientenrekrutierung.query.execution;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Library;
import org.hl7.fhir.r4.model.Measure;
import org.hl7.fhir.r4.model.MeasureReport;
import org.hl7.fhir.r4.model.Bundle.HTTPVerb;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;

/**
 * class for executing CQL queries and
 * send them to an instance of the server Blaze
 * @author Alexandra Banach
 *
 */
public class QueryExecutor {
	
	/**
	 * method to execute a CQL query
	 * @param query created CQL query
	 * @param BlazeURL url of the instance of the server Blaze
	 * @return MeasureReport containing the number of identified patients
	 */
	public MeasureReport executeQuery(String query, String BlazeURL){
		
		
		// generate required UUIDs
		String libraryId = UUID.randomUUID().toString();
		String libraryUrl = UUID.randomUUID().toString();
		String measureId = UUID.randomUUID().toString();
		String measureUrl = UUID.randomUUID().toString();
		
		//query as Base64 and decoded
		query = Base64.getEncoder().encodeToString(query.getBytes());
		byte[] decodedBytes = Base64.getDecoder().decode(query);

		
		// library
		LibraryCreator libraryCreator = new LibraryCreator();
		Library library = libraryCreator.createLibrary(libraryId, libraryUrl, decodedBytes);
		
		// bundle
		BundleCreator bundleCreator = new BundleCreator();
		Bundle bundle = bundleCreator.createBundle(library, libraryId, libraryUrl, BlazeURL);
		
		
		// measure
		MeasureCreator measureCreator = new MeasureCreator();
		Measure measure = measureCreator.createMeasure(measureId, measureUrl);
		measure.addLibrary(libraryUrl);

		// add measure to bundle
		bundle.addEntry().setResource(measure)
		.setFullUrl(BlazeURL + "/Measure/" + measureId)
		.setResource(measure).getRequest().setMethod(HTTPVerb.POST)
		.setUrl(measureUrl);
		//--------------------------------------------execution-----------------------------------
		

		// Log the request
		FhirContext ctx = FhirContext.forR4();
		//System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));
		//System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(measure));


		// Create a client and post the transaction to the server
		IGenericClient client = ctx.newRestfulGenericClient(BlazeURL);
		
	
		
		// execute library
		try{
			client.update().resource(library).execute();
		} catch(BaseServerResponseException e){
			System.out.println(e.getStatusCode());
			System.out.println(e.getResponseBody());
			e.printStackTrace();
		}
		
		// execute measure
		MethodOutcome measureExecution = null;
		try{
			measureExecution = client.update().resource(measure).execute();
		}catch(BaseServerResponseException e){
			System.out.println(e.getStatusCode());
			System.out.println(e.getResponseBody());
			e.printStackTrace();
		}
			
		
		MeasureReport measureReport = null;
		QueryExecutor executor = new QueryExecutor();
		measureReport = executor.getMeasureReport(measureExecution, BlazeURL);
		
		return measureReport;
		
	}
	

	/**
	 * method to fetch MeasureReport from server Blaze
	 * @param queryOutcome methodOutcome
	 * @param serverURL url of the server Blaze
	 * @return measureReport
	 */
	public MeasureReport getMeasureReport(MethodOutcome queryOutcome, String serverURL){
		if(queryOutcome != null){
			// evaluate measure
			FhirContext ctx = FhirContext.forR4();
			IGenericClient client = ctx.newRestfulGenericClient(serverURL);
			IIdType idElement = queryOutcome.getResource().getIdElement();
			try {
				// get current date 
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				String today = dtf.format(now);
		
				// get measure report
				MeasureReport measureReport = client.fetchResourceFromUrl(MeasureReport.class, serverURL + "/Measure/"
						+ idElement.getIdPart() + "/$evaluate-measure?periodStart=2020-01-01&periodEnd=" + today);
				return measureReport;
			} catch (BaseServerResponseException e) {
				e.printStackTrace();
				
				return null;
			}

		} else {
			return null;
		}
	}
}
