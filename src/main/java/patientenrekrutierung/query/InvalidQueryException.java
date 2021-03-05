package patientenrekrutierung.query;

/**
 * Class for modeling an exception
 * which is thrown in the case that no
 * valid CQL query can be generated from
 * the extracted data
 * @author Alexandra Banach
 *
 */
public class InvalidQueryException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * method to throw an exception in the case that no
	 * valid CQL query can be generated from
	 * the extracted data
	 * @param message exception message
	 */
	public InvalidQueryException(String message) {
		super(message);
	}
}
