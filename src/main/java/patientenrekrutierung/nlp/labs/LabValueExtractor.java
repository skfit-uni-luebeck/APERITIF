package patientenrekrutierung.nlp.labs;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import opennlp.tools.tokenize.WhitespaceTokenizer;
import patientenrekrutierung.datastructure.labs.LabUnit;
import patientenrekrutierung.datastructure.labs.LabValue;
import patientenrekrutierung.datastructure.labs.UcumUnits;

/**
 * class for extracting laboratory values from eligibility criteria
 * @author Alexandra Banach
 *
 */
public class LabValueExtractor {
	
	/**
	 * method for extracting laboratory values from eligibility criteria
	 * @param criterion eligibility criterion
	 * @return list containing the extracted laboratory values
	 */
	protected ArrayList<LabValue> extractLabValue(String criterion){
		criterion = criterion.replaceAll("\\,", "");
		// initialize
		ArrayList<LabValue> labvalues = new ArrayList<LabValue>();
		// extract decimal/ integer values
		Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
		Matcher matcher = regex.matcher(criterion);
		
		
		while(matcher.find()){
			if(!criterion.contains("^" + matcher.group(1)) && !criterion.contains(matcher.group(1)+ "^")){
				labvalues.add(new LabValue(matcher.group(1), matcher.start()));
			}		        
		}
			
		Collections.sort(labvalues, Comparator.comparing(LabValue::getPosition));
		
		return labvalues;
	}
	
	
	/**
	 * method for extracting laboratory units from eligibility criteria
	 * @param criterion eligibility criterion
	 * @return list containing the extracted laboratory units
	 * @throws IOException
	 */
	//@SuppressWarnings("serial")
	protected ArrayList<LabUnit> extractLabUnit(String criterion) throws IOException{
		// initialize
		HashSet<LabUnit> labUnits = new HashSet<LabUnit>();		
				
		// get ucum units
		LabValueExtractor extractor = new LabValueExtractor();
		UcumUnits units = extractor.loadFile();
		ArrayList<String> originals = units.getOriginalCodes();
		ArrayList<String> synonyms = units.getSynonymCodes();
		
		// tokenize criteria
		criterion = criterion.replaceAll("\\.", "");
		criterion = criterion.replaceAll("\\,", "");
		WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
		String tokenized[] = tokenizer.tokenize(criterion);		
		
		// check if criteria token equal synonyms of lab units
		for(String t: tokenized){
			for(int i = 0; i<synonyms.size(); i++){
				// if true
					if(t.toLowerCase().equals(synonyms.get(i).toLowerCase())){
						// extract unit and position and add it to set	
						int start = criterion.indexOf(t);
						while(start != -1) {
							labUnits.add(new LabUnit(originals.get(i), criterion.indexOf(t, start)));	
						     start = criterion.indexOf(t, ++start);
						}				
					}
				
				
			}			
		}
		
		// convert set to list and return sorted list
		ArrayList<LabUnit> labUnitList = new ArrayList<LabUnit>(labUnits);
		Collections.sort(labUnitList, Comparator.comparing(LabUnit::getPosition));
		

		return labUnitList;
	}
	
	
	/**
	 * method loading commonly used UCUM units from 
	 * HL7 list
	 * @return object containing two array lists: 1. original UCUM units (e.g. mm[Hg]), 2. synonyms (e.g. mmHg)
	 * @throws IOException
	 */
	private UcumUnits loadFile() throws IOException{
		ArrayList<String> originals = new ArrayList<String>();
		ArrayList<String> synonyms = new ArrayList<String>();
		// obtaining input bytes from a file		
		Resource resource = new ClassPathResource("/UCUM/UCUM.xls");
		InputStream fis = resource.getInputStream();
		
		// creating workbook instance that refers to .xls file
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		// creating a Sheet object to retrieve the object
		HSSFSheet originalUcum = wb.getSheetAt(0);
		HSSFSheet synonym = wb.getSheetAt(1);

		for (Row row : originalUcum) 
		{
			for (Cell cell : row) 
			{
				originals.add(cell.getStringCellValue());
			}
			
		}
		
		for (Row row : synonym) 
		{
			for (Cell cell : row) 
			{
				synonyms.add(cell.getStringCellValue());
				
			}
			
		}
		
		wb.close();
		
		return new UcumUnits(originals, synonyms);
		
	}
}
