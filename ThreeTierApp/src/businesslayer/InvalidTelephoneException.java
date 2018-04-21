package businesslayer;

/**
 * The InvalidTelephoneException class handles Exceptions encountered when users
 * are entering telephone numbers into the program.
 *
 */
public class InvalidTelephoneException extends Exception {

	/**
	 * InvalidTelephoneException constructor accepts a String and returns it as
	 * part of the message style of the Exception Class.
	 * 
	 * @param message
	 *            the message passed to the Exception from the method throwing
	 *            the exception
	 * @return returns the message in the format of the Exception Class
	 */

	public InvalidTelephoneException(String message) {
		super(message);
	}

}
