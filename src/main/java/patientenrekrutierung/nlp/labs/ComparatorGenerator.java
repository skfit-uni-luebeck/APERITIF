package patientenrekrutierung.nlp.labs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import patientenrekrutierung.datastructure.labs.MyComparator;
import patientenrekrutierung.datastructure.labs.ComparatorType;

/**
 * class for extracting comparators to create laboratory entities
 * @author Alexandra Banach
 *
 */
public class ComparatorGenerator {
	/**
	 * method for extracting comparators to create laboratory entities
	 * @param criteria eligibility criteria containing a laboratory entity
	 * @return extracted comparators from eligibility criterion
	 */
	public ArrayList<MyComparator> extractComparator(String criterion) {
		// initialize
		ArrayList<MyComparator> comparators = new ArrayList<MyComparator>();
		// replace some symbols
		criterion = criterion.replaceAll(">=|greater than or equal", "≥");
		criterion = criterion.replaceAll("<=|less than or equal", "≤");
		criterion = criterion.replaceAll("&gt;|greater than", ">");
		criterion = criterion.replaceAll("&lt;|less than", "<");

		// extract ≥
		int i = criterion.indexOf("≥");
		while (i >= 0) {
			comparators.add(new MyComparator(ComparatorType.GREATEREQUAL, i));
			i = criterion.indexOf("≥", i + 1);
		}

		// extract ≤
		int j = criterion.indexOf("≤");
		while (j >= 0) {
			comparators.add(new MyComparator(ComparatorType.LESSEQUAL, j));
			j = criterion.indexOf("≤", j + 1);
		}

		// extract >
		int k = criterion.indexOf(">");
		while (k >= 0) {
			comparators.add(new MyComparator(ComparatorType.GREATER, k));
			k = criterion.indexOf(">", k + 1);
		}
		
		// extract <
		int l = criterion.indexOf("<");
		while (l >= 0) {
			comparators.add(new MyComparator(ComparatorType.LESS, l));
			l = criterion.indexOf("<", l + 1);
		}
		

		// sort list by position of conjunction ascending
		Collections.sort(comparators, Comparator.comparing(MyComparator::getPosition));

		return comparators;
	}
}
