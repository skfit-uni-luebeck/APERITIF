package patientenrekrutierung.nlp.semantics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import gov.nih.nlm.nls.metamap.lite.types.Entity;
import patientenrekrutierung.datastructure.querybuilding.EntityType;
import patientenrekrutierung.datastructure.querybuilding.Subentity;

/**
 * class for generating subentities of medical entities.
 * They contain code, name of entitym and type of entity.
 * @author Alexandra Banach
 *
 */
public class SubentityGenerator {
	
	/**
	 * method for generating subentities of a medical
	 * entity
	 * @param entity medical entity for which subentities should be generated
	 * @param entityTypes type of entity e.g. diagnosis or procedure
	 * @param matchedTexts text of medical entity identified by Metamap Lite
	 * @param blacklist list of terms which should not be searched by Ontoserver requests
	 * @param criterion eligiblity criterion
	 * @return set of subentities 
	 * @throws IOException 
	 */
	public HashSet<Subentity> generateSubentities(Entity entity,Set<EntityType> entityTypes, HashSet<String> matchedTexts, ArrayList<String> blacklist, String criterion) throws IOException{
		CodeSearcher searcher = new CodeSearcher();
		HashSet<Subentity> metamapSubentities = new HashSet<Subentity>();
		// if entity is a pure adjective do not search term or if entity is noun followed by of
		if((entity.getLexicalCategory().matches("JJ") && entity.getMatchedText().contains(" ")) || (entity.getLexicalCategory().matches("NN") && !criterion.contains(entity.getMatchedText() + " of") ) ){
			for(String matches: matchedTexts){	
				// call snomed search if term is not in blacklist
				if(!blacklist.contains(matches.toLowerCase())){
					HashSet<Subentity> subentities = searcher.searchSnomedTermViaOntoServer(matches, entityTypes);
					metamapSubentities.addAll(subentities);
				}else{
					// do not search term if it is in blacklist
				}	
			}
		} else{
			// do not search term as it is not a noun or adjective with noun
		}
	
		// put subs from Hashset into ArrayList to sort it
		ArrayList<Subentity> subents = new ArrayList<Subentity>(metamapSubentities);
		Collections.sort(subents, Comparator.comparing(Subentity::getSubentityType));
		
		HashSet<Subentity> subResult = new HashSet<Subentity>(subents);
	
		return subResult;
	}
}
