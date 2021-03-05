package patientenrekrutierung.query.execution;

import java.util.ArrayList;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntrySearchComponent;
import org.hl7.fhir.r4.model.Bundle.BundleLinkComponent;
import org.hl7.fhir.r4.model.Bundle.HTTPVerb;
import org.hl7.fhir.r4.model.Library;

/**
 * class to create a FHIR resource bundle 
 * which is needed for CQL query execution
 * @author Alexandra Banach
 *
 */
public class BundleCreator {
	/**
	 * method to create a FHIR resource bundle
	 * which is needed for CQL query execution
	 * @param library FHIR resource library
	 * @param libraryId id of library
	 * @param libraryUrl url of library
	 * @param BlazeURL url of an instance of the server Blaze
	 * @return FHIR resource Bundle
	 */
	public Bundle createBundle(Library library, String libraryId, String libraryUrl, String BlazeURL){
		Bundle bundle = new Bundle();
		// type
		bundle.setType(Bundle.BundleType.TRANSACTION);
		// entry
		bundle.addEntry()
		.setFullUrl(BlazeURL + "/Library/" + libraryId)
		.setResource(library).getRequest().setMethod(HTTPVerb.POST)
		.setUrl("XYZ" + libraryUrl);
		BundleEntrySearchComponent besc = new BundleEntrySearchComponent();
		besc = besc.setMode(Bundle.SearchEntryMode.MATCH);
		bundle.addEntry().setSearch(besc);
		// total
		bundle.setTotal(1);
		// link
		ArrayList<BundleLinkComponent> blcList = new ArrayList<BundleLinkComponent>();
		BundleLinkComponent blc = new BundleLinkComponent();
		blc.setRelation("self");
		blc.setUrl(BlazeURL + "/Library?url=" + libraryUrl
				+ "&_count=50&__t=12046&__page-offset=0");
		blcList.add(blc);
		bundle.setLink(blcList);
		
		return bundle;
	}
}
