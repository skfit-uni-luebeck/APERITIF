package patientenrekrutierung.datafetching;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import patientenrekrutierung.datastructure.NctStudy;
import patientenrekrutierung.datastructure.nct.NCTstudy;


/**
 * With this class you can import study data (eligibility criteria)
 * from different sources.
 *  
 * @author Alexandra Banach
 *
 */
public class DataFetcher {
	
	/**
	 * Method to load study data from a text file.
	 * @param filePath path and name of text file which should be imported
	 * @return content from text file: eligibility criteria of study as String
	 */
	public String loadTxtFile(String filePath){
		String content = "";

		try {
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
	
	/**
	 * Method to load data of a study via REST. The study must be registered
	 * in ClinicalTrials.gov
	 * @param nctnumber trial number in ClinicalTrials.gov
	 * @return data of study which is registered in ClinicalTrial.gov
	 */
	public NctStudy loadDataWithRest(String nctnumber) {
		// initialize 

		String criteria = null;
		String gender = null;
		String minimum_age = null;
		String maximum_age = null;
		
		// getting data from NCT REST API
		// initialize
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
		// Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// Note: here we are making this converter to process any kind of response, 
		// not only application/*json, which is the default behaviour
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
		messageConverters.add(converter);  
		restTemplate.setMessageConverters(messageConverters); 
		// data fetching		
		NCTstudy nct = restTemplate.getForObject("https://clinicaltrials.gov/api/query/study_fields?expr="+ nctnumber +"&fields=NCTId,EligibilityCriteria,Gender,MinimumAge,MaximumAge&fmt=json", NCTstudy.class);
		
		// filling variables
		criteria = nct.getStudyFieldsResponse().getStudyFields().get(0).getEligibilityCriteria().get(0);
		criteria = criteria.replaceAll("\n\n", "\n - ");
		criteria = criteria.replaceAll("\n", "\n - ");
		criteria = criteria.replaceAll("-  - ", "- ");
		criteria = criteria.replaceAll("- Exclusion criteria", "Exclusion criteria");
		
		gender = nct.getStudyFieldsResponse().getStudyFields().get(0).getGender().get(0);
		
		try{
			minimum_age = nct.getStudyFieldsResponse().getStudyFields().get(0).getMinimumAge().get(0);
		} catch(Exception e){
			minimum_age = "";
		}
		
		try{
			maximum_age = nct.getStudyFieldsResponse().getStudyFields().get(0).getMaximumAge().get(0).toString();
		} catch(Exception e){
			maximum_age = "";
		}
		
		// put variables into hashmap and return it
		NctStudy nctStudy = new NctStudy(criteria, gender, minimum_age, maximum_age);
		
		return nctStudy;
	}
		
	/**
	 * Download the study document of a trial which is
	 * registered in ClinicalTrials.gov. After this you
	 * can import your study data with this method.
	 * @param path path and name of XML file with study data from ClinicalTrials.gov which should be imported
	 * @return
	 */
	public NctStudy loadFile(String path) {
		// initialize 
		String criteria = null;
		String gender = null;
		String minimum_age = null;
		String maximum_age = null;
		
		// getting data of xml file
		try{
			//creating a constructor of file class and parsing an XML file  
			File file = new File(path);  
			//an instance of factory that gives a document builder  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			//an instance of builder to parse the specified xml file  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			FileInputStream in = new FileInputStream(file);
			Document doc = db.parse(in, "UTF-8");
			doc.getDocumentElement().normalize();  
			
			// getting inclusion and exclusion criteria
			NodeList nodeList = doc.getElementsByTagName("criteria");  
			Node node = nodeList.item(0);  
			
			
			if (node.getNodeType() == Node.ELEMENT_NODE){  
				Element eElement = (Element) node; 
				criteria = eElement.getElementsByTagName("textblock").item(0).getTextContent();
			}
			
			// get gender value
			NodeList genderList = doc.getElementsByTagName("gender");
			gender = genderList.item(0).getTextContent();
			
			// get minimum age
			NodeList minimumAge = doc.getElementsByTagName("minimum_age");
			minimum_age = minimumAge.item(0).getTextContent();
			
			// get maximum age
			NodeList maximumAge = doc.getElementsByTagName("maximum_age");
			maximum_age = maximumAge.item(0).getTextContent();
			
		// if something goes wrong...	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// put results into object and return it
		NctStudy nctStudy = new NctStudy(criteria, gender, minimum_age, maximum_age);
		
		
		return nctStudy;
		
	}
	
}
