package patientenrekrutierung.nlp.semantics;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import bioc.BioCDocument;
import gov.nih.nlm.nls.metamap.document.FreeText;
import gov.nih.nlm.nls.metamap.lite.types.Entity;
import gov.nih.nlm.nls.ner.MetaMapLite;
import patientenrekrutierung.datastructure.EntityCollection;

/**
 * class for performing entity recognition for medical
 * concepts (diagnoses, medications, laboratory results and procedures)
 * using Metamap Lite
 * @author Alexandra Banach
 *
 */
public class MetaMapper {
	/**
	 * method for performing entity recognition for medical
	 * concepts (diagnoses, medications, laboratory results and procedures)
	 * using Metamap Lite
	 * @param criterion eligibility criterion from which entities should be extracted
	 * @return entity collection containing all extracted information
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public EntityCollection metaMapping(String criterion) throws InvocationTargetException, Exception{
		// initialize
		Properties myProperties = new Properties();
		Resource resource = new ClassPathResource("/metamaplite.properties");
		InputStream inputStream = resource.getInputStream();
		myProperties.load(inputStream);
		//myProperties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("metamaplite.properties"));
		MetaMapLite metaMapLiteInst = new MetaMapLite(myProperties);
		List<BioCDocument> documentList = new ArrayList<BioCDocument>();
		documentList.add(FreeText.instantiateBioCDocument(criterion));	
		//Getting a list of entities for the document list:
		List<Entity> entityList = metaMapLiteInst.processDocumentList(documentList);
		
		// new try if entity list is empty: remove all "-" in text and try again 
		if(entityList.size() == 0){
			criterion = criterion.replaceAll("[-]", " ");
			documentList.add(FreeText.instantiateBioCDocument(criterion));
			entityList = metaMapLiteInst.processDocumentList(documentList);
		}
		
		// process result with snowstorm etc.
		EntityGenerator entityGenerator = new EntityGenerator();
		EntityCollection metamapEntities = entityGenerator.generateEntity(entityList, criterion);
		

		return metamapEntities;

	}
}
