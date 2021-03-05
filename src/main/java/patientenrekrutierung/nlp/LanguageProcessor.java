package patientenrekrutierung.nlp;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

/**
 * class providing different NLP methods
 * for pipelines
 * @author Alexandra Banach
 *
 */
public class LanguageProcessor {
	
	/**
	 * performing replacement of some term for a
	 * better entity recognition
	 * @param eligibility eligibility criterion
	 * @return processed eligibility criterion
	 */
	protected String replaceTerms(String eligibility){
		eligibility = eligibility.replaceAll("breast-feeding|breastfeeding|lacting|lactation|nursing|lactating", "breast feeding");
		eligibility = eligibility.replaceAll("pregnant", "pregnancy");
		eligibility = eligibility.replaceAll("µ", "u");
		eligibility = eligibility.replaceAll("exception|exceptions|other than|excluding", "except");
		
		return eligibility;
	}
	
	/**
	 * separating inclusion and exclusion criteria
	 * @param eligibilityCriteria all eligiblity criteria of a study
	 * @return splitted eligiblity criteria (inclusion separated from exclusion criteria)
	 */
	protected String[] splitEligibilityCriteria(String eligibilityCriteria){
		// ensure returning empty strings if no inclusion or exclusion found
		String inclusion = "";
		String exclusion = "";
		
		// split inclusion and exclusion criteria
		if (eligibilityCriteria.contains("exclusion criteria")) {
			String myNewData[] = eligibilityCriteria.toLowerCase().split("exclusion criteria");
			inclusion = myNewData[0];
			exclusion = myNewData[1];
		} else {
			inclusion = eligibilityCriteria;
		}
		
		// put inclusion and exclusion criteria into string array and return
		String[] criteria = new String[2];
		criteria[0] = inclusion;
		criteria[1] = exclusion;

		return criteria;
	}
	
	/**
	 * method to perform eligibility criteria
	 * processing: punctuation removal, whitespace tokenizing,
	 * stop word removal, lemmatization
	 * @param criteria eligibility criteria
	 * @return processed eligiblity criteria
	 * @throws IOException
	 */
	protected ArrayList<String> processCriteria(String[] criteria) throws IOException{
		// initialize
		LanguageProcessor nlp = new LanguageProcessor();
		
		// remove punctuations
		criteria = nlp.removePunctuations(criteria);
		
		// tokenizing with whitespaces	
		ArrayList<String[]> criteriaList = nlp.whiteSpaceTokenize(criteria);
		// removing stopwords
		ArrayList<String[]> criteriaListWithoutStopwords = new ArrayList<String[]>();
		for(String[] e : criteriaList){
			criteriaListWithoutStopwords.add(nlp.removeStopwords(e));
		}
		// lemmatization 
		ArrayList<String[]> criteriaListAfterLemmatizing = new ArrayList<String[]>();
		for(String[] e : criteriaListWithoutStopwords){
			criteriaListAfterLemmatizing.add(nlp.lemmatize(e));
		}
		// put words of single criteria together again
		ArrayList<String> processedCriteria = new ArrayList<String>();
		for(String[] e : criteriaListAfterLemmatizing){
			String sentence = nlp.wordsToSentence(e);
			if(!sentence.matches("")){
				processedCriteria.add(sentence);
			}else{
				
			}
				
		}
		
		return processedCriteria;				
	}
	
	/**
	 * method for performing punctuation removal
	 * @param criteria eligibility criterion
	 * @return eligibility criterion without punctuation
	 */
	protected String[] removePunctuations(String[] criteria){
		for(int i = 0; i < criteria.length; i++){
			// replace any char of from \p{Punct} class except /, =, < and > by empty char
			criteria[i] = criteria[i].replaceAll("[\\p{Punct}&&[^\\/<>=-^.%]]+", "");
		}
		return criteria;
	}
	
	/**
	 * method for performing whitespace tokenizing
	 * @param criteria eligibility criteria
	 * @return tokenized criteria
	 */
	protected ArrayList<String[]> whiteSpaceTokenize(String[] criteria){
		ArrayList<String[]> stringList = new ArrayList<String[]>();
		WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
		String[] tokens = null;
		for(int i = 0; i < criteria.length; i++){
			tokens = tokenizer.tokenize(criteria[i]);
			stringList.add(tokens);
		}
		return stringList;
	}
	
	ApplicationContext context;
	/**
	 * method for performing stop word removal
	 * @param string tokenized eligibility criterion
	 * @return eligiblity criterion without stop words
	 * @throws IOException
	 */
	public String[] removeStopwords(String[] string) throws IOException{
		// initialize
		ArrayList<String> results = new ArrayList<String>();
		List<String> stopwords = new ArrayList<String>(); 
		
		// load stop word list
	    Resource resource = new ClassPathResource("/Models/en.txt");
	    InputStream stream = resource.getInputStream();
	    String content = StreamUtils.copyToString(stream, Charset.defaultCharset());
		String[] splitted = content.split("\n");
		for(String s: splitted){
			stopwords.add(s);	
		}
	 
		
	      for(String s: string){
	    	  // if word s in string is not a stopword put it into results
	    	  if(!stopwords.contains(s)){
	    		 results.add(s);
	    	  }else{
	    		  
	    	  }
	      }
	      
	     // convert list to array and return it
	     String[] result = new String[results.size()];   
	     for(int i=0; i<result.length; i++){
	    	 result[i] = results.get(i);
	     }
	    
	    return result;
	}
	
	
	/**
	 * method for performing lemmatization
	 * @param tokens tokenized criterion
	 * @return lemmatized criterion
	 * @throws IOException
	 */
	protected String[] lemmatize(String[] tokens) throws IOException{
		// load models and initialize objects
		Resource resource = new ClassPathResource("/Models/en-pos-maxent.bin");
		InputStream inputStreamPOSTagger = resource.getInputStream();
		//InputStream inputStreamPOSTagger = new FileInputStream("/Models/en-pos-maxent.bin"); 
		POSModel posModel = new POSModel(inputStreamPOSTagger);
		POSTaggerME posTagger = new POSTaggerME(posModel);
		String tags[] = posTagger.tag(tokens);
		//InputStream dictLemmatizer = new FileInputStream("src\\main\\resources\\Models\\en-lemmatizer.dict");
		Resource resource2 = new ClassPathResource("/Models/en-lemmatizer.dict");
		InputStream dictLemmatizer = resource2.getInputStream();
		DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		// perform lemmatization		
		String[] lemmas = lemmatizer.lemmatize(tokens, tags);
		// Remove empty lemmas O from string result array, instead take former expression (tokens)
		ArrayList<String> lemmasAsList = new ArrayList<String>();
		for(int i =0; i< lemmas.length; i++){
			if(lemmas[i] != "O"){
				lemmasAsList.add(lemmas[i]);
			} else{
				lemmasAsList.add(tokens[i]);
			}
		}
		// From list back to array again
		String[] result = new String[lemmasAsList.size()];
		for(int i = 0; i < lemmasAsList.size(); i++){
			result[i] = lemmasAsList.get(i);
		}
		
		return result;
	
	}
	
	/**
	 * put single words of a criterion together to
	 * perform a sentence
	 * @param words tokenized eligibility criterion
	 * @return processed criterion as string
	 */
	public String wordsToSentence(String[] words){
		String sentence = "";
		// take words of array and put them together as sentence in single string
		if(words.length >= 1 && !words[0].matches("")){
			sentence = words[0];
			for(int i = 1; i < words.length; i++){
				 sentence = sentence +" " + words[i];	
				}
		}else{
			
		}
				
		
		return sentence;
	}
	

	
	
}
