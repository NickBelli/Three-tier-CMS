package businesslayer;

/**
 * The Telephone Number class cleans and stores Phone Numbers for use in any of
 * the main program areas. It is designed so that as long as an entry has the
 * correct number of digits, it will still parse a phone number no matter what
 * the user enters.
 * 
 * Standard entries generally have examples such as:
 * <ul>
 * <li>999 867 5309</li>
 * <li>(999)867-5309</li>
 * <li>1 (999) 867-5309</li>
 * <li>999.867.5309</li>
 * <li>9998675309</li>
 * <li>1 999 867-5309</li>
 * </ul>
 */

public class TelephoneNumber {

	private String cleanNumber;
	private String longDistance = "";
	private String areaCode;
	private String exchange;
	private String localNumber;

	/**
	 * The Constructor method will fail and throw an exception if a user enters
	 * an invalid phone number. Invalid phone numbers are those with less than
	 * 11 digits if beginning with a "1" or less than 10 digits if beginning
	 * with Area Code.
	 * 
	 * If a phone number starts with "1", the constructor will disregard that
	 * when creating the cleanNumber string for parsing and will hard code that
	 * number in a separate variable.
	 *
	 * @version 1
	 * @param phoneNumber
	 *            the phone number entered by the user and passed into the
	 *            Constructor. The constructor cleans and checks the phone
	 *            number prior to assigning the strings and substrings.
	 * @param stringCleaner(phoneNumber)
	 *            the phone number after it has been sent through the cleaner
	 *            method
	 * @exception InvalidTelephoneException
	 *                throws an error if the validation fails either test.
	 * @see stringCleaner(String phoneNumber)
	 */

	public TelephoneNumber(String phoneNumber) throws InvalidTelephoneException {

		// Test to see if the phone number starts with a 1 and is 11 digits
		if (!isValidLongDistanceNumber(stringCleaner(phoneNumber))) {
			throw new InvalidTelephoneException("The phone number entered must be 11 digits if starting with a 1");
		}
		// Test to see if the phone number starts with something other than 1
		// and is 10 digits
		if (!isValidLocalNumber(stringCleaner(phoneNumber))) {
			throw new InvalidTelephoneException("The phone number entered must be 10 digits or start with a 1");
		} else {
			setCleanNumber(stringCleaner(phoneNumber));
			// Set the long distance code to 1 and begin the phone number on the
			// 2nd entered number if 11 digits
			if (stringCleaner(phoneNumber).startsWith("1")) {
				setCleanNumber(stringCleaner(phoneNumber).substring(1));
				longDistance = "1";
			}
			setAreaCode(cleanNumber);
			setExchange(cleanNumber);
			setLocalNumber(cleanNumber);
		}
	}

	/**
	 * Retrieves the phone number entered by the user after it has been cleaned
	 * of any character that is not a digit and passed validation
	 * 
	 * @return cleanNumber - the phone number in a 10 digit or 11 digit string.
	 */
	public String getCleanNumber() {

		return cleanNumber;
	}

	/**
	 * Sets the phone number instance variable after the user entry has passed
	 * validation
	 * 
	 * @param phoneNumber
	 *            the phoneNumber that has been checked from the stringCleaner()
	 * @see stringCleaner(String phoneNumber)
	 */
	public void setCleanNumber(String phoneNumber) {

		this.cleanNumber = phoneNumber;
	}

	/**
	 * Retrieves the area code of the instance.
	 * 
	 * @return areaCode - the area code in a 3 digit string.
	 */

	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * Sets the instance area code based on the first 3 digits of the validated
	 * phone number.
	 * 
	 * @param phoneNumber
	 *            this is the String phone number after it has been normalized.
	 */
	public void setAreaCode(String phoneNumber) {

		this.areaCode = phoneNumber.substring(0, 3);
	}

	/**
	 * Retrieves the exchange code of the instance.
	 * 
	 * @return exchange - the exchange code in a 3 digit string.
	 */
	public String getExchange() {
		return exchange;
	}

	/**
	 * Sets the instance exchange code based on the second 3 digits of the
	 * validated phone number.
	 * 
	 * @param phoneNumber
	 *            this is the String phone number after it has been normalized.
	 */
	public void setExchange(String phoneNumber) {

		this.exchange = phoneNumber.substring(3, 6);
	}

	/**
	 * Retrieves the local number of the instance.
	 * 
	 * @return localNumber - the local number in a 4 digit string.
	 */
	public String getLocalNumber() {

		return localNumber;
	}

	/**
	 * Sets the instance local number code based on the last 4 digits of the
	 * validated phone number.
	 * 
	 * @param phoneNumber
	 *            this is the String phone number after it has been normalized.
	 */
	public void setLocalNumber(String phoneNumber) {

		this.localNumber = phoneNumber.substring(6, 10);
	}

	/**
	 * Normalizes the phone number to a string of digits by only pulling in the
	 * characters that are digits and creating the string from those digits.
	 * 
	 * @param phoneNumber
	 *            this is the user entered string for validation and cleaning
	 * @return a String of digits that is suitable for validation.
	 */
	public String stringCleaner(String phoneNumber) {
		String cleanPhoneNumber = "";
		for (char c : phoneNumber.toCharArray()) {
			if (Character.isDigit(c)) {
				cleanPhoneNumber += c;
			}
		}
		return cleanPhoneNumber;
	}

	/**
	 * This method tests to see if the phone number starts with a 1 and is 11
	 * digits.
	 * 
	 * @param phoneNumber
	 *            the phone number passed to the method
	 * @return true/false depending on validation check
	 */
	public boolean isValidLongDistanceNumber(String phoneNumber) {
		if (phoneNumber.startsWith("1") && phoneNumber.length() != 11) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method tests to see if the phone number starts with a number other
	 * than 1 and is 10 digits.
	 * 
	 * @param phoneNumber
	 *            the phone number passed to the method
	 * @return true/false depending on validation check
	 */
	public boolean isValidLocalNumber(String phoneNumber) {
		if ((!phoneNumber.startsWith("1")) && phoneNumber.length() != 10) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method overrides the Object.toString() method to allow the program
	 * to set the proper formatting of a phone number depending on whether it is
	 * local or long distance.
	 * 
	 * @return a local method variable that holds the instance's return message
	 *         in the toString() method.
	 */
	@Override
	public String toString() {
		String formattedNumber = "";
		if (this.longDistance.equals("1")) {
			formattedNumber = "1 " + "(" + getAreaCode() + ") " + getExchange() + "-" + getLocalNumber();
		} else {
			formattedNumber = "(" + getAreaCode() + ") " + getExchange() + "-" + getLocalNumber();
		}
		return formattedNumber;
	}

}
