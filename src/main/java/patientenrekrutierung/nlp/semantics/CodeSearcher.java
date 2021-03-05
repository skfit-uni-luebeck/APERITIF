package patientenrekrutierung.nlp.semantics;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import patientenrekrutierung.datastructure.ontoserver.Contain;
import patientenrekrutierung.datastructure.ontoserver.OntoServerResult;
import patientenrekrutierung.datastructure.querybuilding.EntityType;
import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for requesting codes e.g.
 * SNOMED CT and LOINC codes via an
 * instance of the Ontoserver
 * @author Alexandra Banach
 *
 */
public class CodeSearcher {
	
	/**
	 * method for requesting disorder, medication and procedure codes
	 * from SNOMED CT via Ontoserver
	 * @param term search term
	 * @param entityTypes set of possibile types of an entity
	 * @return set of extracted codes
	 * @throws IOException 
	 */
	protected HashSet<Subentity> searchSnomedTermViaOntoServer(String term, Set<EntityType> entityTypes) throws IOException{
		// initalize
		HashSet<Subentity> snomedResults = new HashSet<Subentity>();
		CodeSearcher snomedSearcher = new CodeSearcher();
		
		// get disorders, medications and procedures
		for(EntityType e: entityTypes){
			if(e.equals(EntityType.Disease) && !term.matches("disorder") ){
				
				HashSet<Contain> containsDisorders = snomedSearcher.searchSnomedDisorders(term);
				// put disorders into result list
				if(!containsDisorders.isEmpty()){
					for(Contain contain: containsDisorders){
						snomedResults.add(new Subentity(contain.getCode(), contain.getDisplay(), EntityType.Disease));
					}
				}
			
			}else if(e.equals(EntityType.Procedure)){
				HashSet<Contain> containsProcedures = snomedSearcher.searchSnomedProcedures(term);
				// put procedures into result list
				if(!containsProcedures.isEmpty()){
					for(Contain contain: containsProcedures){
						snomedResults.add(new Subentity(contain.getCode(), contain.getDisplay(), EntityType.Procedure));
					}
				}
			}else if(e.equals(EntityType.Medication)){
				HashSet<Contain> containsMedications = snomedSearcher.searchSnomedMedications(term);
				// put medications into result list
				if(!containsMedications.isEmpty()){
					for(Contain contain: containsMedications){
						snomedResults.add(new Subentity(contain.getCode(), contain.getDisplay(), EntityType.Medication));
					}
				}
			}
		}		
		return snomedResults;
	}
	
	/**
	 * method for requesting SNOMED CT laboratory codes 
	 * via Ontoserver
	 * @param term search term (name of laboratory entity)
	 * @return set of SNOMED CT laboratory codes
	 * @throws IOException 
	 */
	public HashSet<Contain> searchSnomedLabs(String term) throws IOException{
		// initialize
		ArrayList<Contain> firstQueryResult = new ArrayList<Contain>();
		OntoServerResult resObservableEntity = new OntoServerResult();
		OntoServerResult resEvaluationProcedure = new OntoServerResult();
		
		// get Ontoserver url from properties file
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("/ontoserver.properties");
		InputStream inputStream = resource.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		properties.load(bufferedInputStream);
		bufferedInputStream.close();
		String url = properties.getProperty("urlOntoserver");
		
		// old version
		/*Properties properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream("src\\main\\resources\\ontoserver.properties"));
		properties.load(stream);
		stream.close();
		String url = properties.getProperty("urlOntoserver");*/

		// search labs
		RestTemplate restTemplate = new RestTemplate();
		resObservableEntity = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/363787002&filter=" + term, OntoServerResult.class);
		resEvaluationProcedure = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/386053000&filter=" + term, OntoServerResult.class);
		
			
		// add all results into one list
		if (resObservableEntity.getExpansion().getContains() != null) {
			firstQueryResult.addAll(resObservableEntity.getExpansion().getContains());
		}
		if (resEvaluationProcedure.getExpansion().getContains() != null) {
			firstQueryResult.addAll(resEvaluationProcedure.getExpansion().getContains());
		}
		
		HashSet<Contain> eclResult = new HashSet<Contain>();
		if(firstQueryResult != null){
			// execute ecl query
			CodeSearcher snomedsearcher = new CodeSearcher();
			eclResult = snomedsearcher.eclQueryExecution(firstQueryResult);
		}	
		return eclResult;
	}
	
	/**
	 * method for requesting LOINC codes 
	 * via Ontoserver
	 * @param term search term (name of laboratory entity)
	 * @return set of extracted LOINC codes
	 * @throws IOException 
	 */
	public HashSet<Contain> searchLoincLabs(String term) throws IOException{
		// initialize
		OntoServerResult resultLoincSearch = new OntoServerResult();
		HashSet<Contain> loincResult = new HashSet<Contain>();
		
		// get Ontoserver url from properties file
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("/ontoserver.properties");
		InputStream inputStream = resource.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		properties.load(bufferedInputStream);
		bufferedInputStream.close();
		String url = properties.getProperty("urlOntoserver");
		
		// search labs in loinc
		RestTemplate restTemplate = new RestTemplate();
		resultLoincSearch = restTemplate.getForObject(url + "ValueSet/$expand?url=http://loinc.org/vs&filter=" + term, OntoServerResult.class);
				
		// put result of search into hashset 
		if(resultLoincSearch.getExpansion().getContains() != null){
			loincResult.addAll(resultLoincSearch.getExpansion().getContains());
		}	
		return loincResult;		
	}
	
	/**
	 * method for requesting SNOMED CT disorder
	 * codes via Ontoserver
	 * @param term search term (name of disorder)
	 * @return set of extracted SNOMED CT Codes codes for disorder
	 * @throws IOException 
	 */
	private HashSet<Contain> searchSnomedDisorders(String term) throws IOException{
		// initialize
		ArrayList<Contain> firstQueryResult = new ArrayList<Contain>();
		OntoServerResult resClinicalFinding = new OntoServerResult();
		OntoServerResult resEvent = new OntoServerResult();
		OntoServerResult resSituation = new OntoServerResult();
		
		// get Ontoserver url from properties file
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("/ontoserver.properties");
		InputStream inputStream = resource.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		properties.load(bufferedInputStream);
		bufferedInputStream.close();
		String url = properties.getProperty("urlOntoserver");
		
		// search disorders
		RestTemplate restTemplate = new RestTemplate();
		resClinicalFinding = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/404684003&filter=" + term, OntoServerResult.class);
		resEvent = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/272379006&filter=" + term, OntoServerResult.class);
		resSituation = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/243796009&filter=" + term, OntoServerResult.class);
	
		
		// add all results into one list
		if(resClinicalFinding.getExpansion().getContains() != null){
			firstQueryResult.addAll(resClinicalFinding.getExpansion().getContains());
		}
		if(resEvent.getExpansion().getContains() != null){
			firstQueryResult.addAll(resEvent.getExpansion().getContains());
		}
		if(resSituation.getExpansion().getContains() != null){
			firstQueryResult.addAll(resSituation.getExpansion().getContains());
		}
		
		HashSet<Contain> eclResult = new HashSet<Contain>();
		if(firstQueryResult != null){
			// execute ecl query
			CodeSearcher snomedsearcher = new CodeSearcher();
			eclResult = snomedsearcher.eclQueryExecution(firstQueryResult);
		}		
		return eclResult;
	}
	
	/**
	 * method for requesting SNOMED CT medication codes
	 * via Ontoserver
	 * @param term search term (name of medication)
	 * @return set of extracted SNOMED CT codes for medication
	 * @throws IOException 
	 */
	private HashSet<Contain> searchSnomedMedications(String term) throws IOException{
		// initialize
		List<Contain> firstQueryResult = new ArrayList<Contain>();
		OntoServerResult resDrug = new OntoServerResult();
		OntoServerResult resProduct = new OntoServerResult();
		OntoServerResult resSubstance = new OntoServerResult();
		
		// get Ontoserver url from properties file
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("/ontoserver.properties");
		InputStream inputStream = resource.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		properties.load(bufferedInputStream);
		bufferedInputStream.close();
		String url = properties.getProperty("urlOntoserver");
		
		// search medications
		RestTemplate restTemplate = new RestTemplate();
		resDrug = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/410942007&filter=" + term, OntoServerResult.class);
		resProduct = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/373873005&filter=" + term, OntoServerResult.class);
		resSubstance = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/106181007&filter=" + term, OntoServerResult.class);
		
		
		// add all results into one list
		if(resDrug.getExpansion().getContains() != null){
			firstQueryResult.addAll(resDrug.getExpansion().getContains());
		}
		if(resProduct.getExpansion().getContains() != null){
			firstQueryResult.addAll(resProduct.getExpansion().getContains());
		}
		if(resSubstance.getExpansion().getContains() != null){
			firstQueryResult.addAll(resSubstance.getExpansion().getContains());
		}
		
		HashSet<Contain> eclResult = new HashSet<Contain>();
		if(firstQueryResult != null){
			// execute ecl query
			CodeSearcher snomedsearcher = new CodeSearcher();
			eclResult = snomedsearcher.eclQueryExecution(firstQueryResult);
		}
		return eclResult;	
	}
	
	/**
	 * method for requesting SNOMED CT procedure codes
	 * via Ontoserver
	 * @param term search term (name of procedure)
	 * @return set of extracted SNOMED CT procedure codes
	 * @throws IOException 
	 */
	private HashSet<Contain> searchSnomedProcedures(String term) throws IOException{
		// initialize
		RestTemplate restTemplate = new RestTemplate();
		OntoServerResult resProcedure = new OntoServerResult();
		List<Contain> containsProcedure = new ArrayList<Contain>();
		
		// get Ontoserver url from properties file
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("/ontoserver.properties");
		InputStream inputStream = resource.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		properties.load(bufferedInputStream);
		bufferedInputStream.close();
		String url = properties.getProperty("urlOntoserver");
		
		// search procedures
		resProcedure = restTemplate.getForObject(url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=isa/71388002&filter=" + term , OntoServerResult.class);		
		containsProcedure = resProcedure.getExpansion().getContains();
		
		HashSet<Contain> eclResult = new HashSet<Contain>();
		if(containsProcedure != null){
			// execute ecl query
			CodeSearcher snomedsearcher = new CodeSearcher();
			eclResult = snomedsearcher.eclQueryExecution(containsProcedure);
		}
				
		return eclResult;

	}
	
	/**
	 * method for performing ECL request to
	 * extract subconcepts of SNOMED CT concepts
	 * @param queryResult list of extracted SNOMED CT codes for which subconcepts should be extracted
	 * @return set of SNOMED CT codes including codes of their subconcepts
	 * @throws IOException 
	 */
	private HashSet<Contain> eclQueryExecution(List<Contain> queryResult) throws IOException{
		// initialize
		HashSet<Contain> result = new HashSet<Contain>();
		
		// get Ontoserver url from properties file
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("/ontoserver.properties");
		InputStream inputStream = resource.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		properties.load(bufferedInputStream);
		bufferedInputStream.close();
		String url = properties.getProperty("urlOntoserver");
		
		// only if there is a former query result
		if(!queryResult.isEmpty()){
			String ecl = url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=ecl/";
			// iterate in blocks of 450, otherwise the ecl query is getting too long
			int upperLimit = 250;
			// check if upperlimit is too high for query result list
			if(upperLimit > queryResult.size()){
				upperLimit = queryResult.size();
			}
			
			// start iterating
			int lowerLimit = 0;
			while(upperLimit <=queryResult.size()&& lowerLimit!=upperLimit){
				// iterate in block
				for(int i= lowerLimit; i<upperLimit; i++){
					// build query
					ecl = ecl + "<<" + queryResult.get(i).getCode() + "OR";	
				}
				// cut off last or
				ecl = ecl.substring(0, ecl.length() - 2);
				// perform query and add result to list
				RestTemplate restTemplate = new RestTemplate();
				try{
					OntoServerResult res = restTemplate.getForObject(ecl, OntoServerResult.class);
					result.addAll(res.getExpansion().getContains());
				}catch(Exception e){
					// continue
				}
				
				// re-initialize for next block
				ecl = url + "ValueSet/$expand?url=http://snomed.info/sct?fhir_vs=ecl/";
				// set new limits to iterate for next block
				lowerLimit = upperLimit;
				upperLimit = upperLimit + 250;
				// check if end of list is reached with block
				if(upperLimit> queryResult.size()){
					upperLimit = queryResult.size();
				}
			}
		}

		return result;
	}
	
	/**
	 * method to identify the semantic types of a medical entity
	 * by using the extracted UMLS semantic types
	 * @param semanticTypeSet extracted UMLS semantic types from Metamap Lite
	 * @return identified entity types
	 */
	protected Set<EntityType> extractEntityTypes(Set<String> semanticTypeSet){
		Set<EntityType> entityTypes = new HashSet<EntityType>();
		for (String s : semanticTypeSet) {
			if (s.matches("acab|anab|bact|cgab|dsyn|inpo|mobd|neop|patf|sosy")) {
				entityTypes.add(EntityType.Disease);
			} else if (s.matches(
					"antb|bodm|chem|chvf|chvs|clnd|elii|enzy|hops|horm|imft|inch|irda|nnon|orch|phsu|rcpt|vita")) {
				entityTypes.add(EntityType.Medication);
			} else if (s.matches("diap|edac|hlca|lbpf|mbrt|resa|topp")) {
				entityTypes.add(EntityType.Procedure);
			} else if (s.matches("lbtr|lbpr|cell|bacs|aapp")) {
				entityTypes.add(EntityType.Lab);
			}
		}
		return entityTypes;
	}
}
