package businesslayer;

import java.util.Comparator;

/**
 * The Person class stores name, phone number, and email entries entered in the
 * JavaFX GUI. It stores all information as Strings that can then be
 * concatenated together in the final toString method.
 * 
 * No data validation has yet been implemented, but is intended to be
 * implemented should this interface go further in its lifespan.
 *
 */
public class Person {

	private int id;
	private String personName;
	private String personEmail;
	private String personPhone;

	public Person() {

	}

	/**
	 * The Constructor Override method accepts 3 string parameters.
	 * 
	 * @param name
	 *            - name of the person
	 * @param email
	 *            - email address of the person
	 * @param phone
	 *            - phone number of the person
	 */
	public Person(String name, String email, String phone) {
		setName(name);
		setEmail(email);
		setPhone(phone);
	}

	public Person(int id, String name, String email, String phone) {
		setId(id);
		setName(name);
		setEmail(email);
		setPhone(phone);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Retrieves the name of the person
	 * 
	 * @return personName
	 */
	public String getName() {
		return personName;
	}

	/**
	 * Sets the person's name based on the String passed from the GUI
	 * 
	 * @param name
	 *            - the name passed in from the GUI
	 */
	public void setName(String name) {
		this.personName = name;
	}

	/**
	 * Retrieves the email address associated with the person
	 * 
	 * @return personEmail
	 */
	public String getEmail() {
		return personEmail;
	}

	/**
	 * Sets the email of the person based on String passed in from the GUI
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.personEmail = email;
	}

	/**
	 * Retrieves the phone number associated with the person
	 * 
	 * @return personPhone
	 */
	public String getPhone() {
		return personPhone;
	}

	/**
	 * Sets the phone number of a person based on the information passed in from
	 * the GUI
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.personPhone = phone;
	}

	/**
	 * Concatenates the three fields of the constructor with other text to give
	 * a readable data set for printing to console.
	 */
	public String toString() {
		String person = "Name: " + personName + "\tEmail: " + personEmail + "\tPhone: " + personPhone;
		return person;
	}

	/**
	 * This Comparator class is created to compare the names of each person in a
	 * list of entries.
	 * 
	 */
	public static Comparator<Person> PersonNameComparator = new Comparator<Person>() {

		/**
		 * Compares two person entries based on Name string.
		 * 
		 * @param person1
		 *            - the first person to be evaluated
		 * @param person2
		 *            - the second person to be evaluated
		 * @return personName1.compareTo(personName2) - or the value of which
		 *         String is greater in ASCII value alphabetically (natural
		 *         order)
		 */
		public int compare(Person person1, Person person2) {
			String personName1 = person1.getName().toUpperCase();
			String personName2 = person2.getName().toUpperCase();

			return personName1.compareTo(personName2);
		}
	};
}