package patientenrekrutierung.query.execution;

import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.Library;
import org.hl7.fhir.r4.model.Meta;

/**
 * class to create a FHIR resource library 
 * which is needed for CQL query execution
 * @author Alexandra Banach
 *
 */
public class LibraryCreator {
	/**
	 * method to create a FHIR resource library 
	 * which is needed for CQL query execution
	 * @param libraryId id of library
	 * @param libraryUrl url of library
	 * @param decodedQuery decoded CQL query
	 * @return FHIR resource Library
	 */
	public Library createLibrary(String libraryId, String libraryUrl, byte[] decodedQuery){
		Library library = new Library();
		// url
		library.setUrl(libraryUrl);
		// id
		library.setId(libraryId);
		// meta
		Meta metaLib = new Meta();
		metaLib.setVersionId("12017");
		library.setMeta(metaLib);
		// content
		Attachment attachment = new Attachment();
		attachment.setContentType("text/cql");
		// attachment.setData(test.getBytes());
		attachment.setData(decodedQuery);
		library.addContent(attachment);
		
		return library;
	}
}
