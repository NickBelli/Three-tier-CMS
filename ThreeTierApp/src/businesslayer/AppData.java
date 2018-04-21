package businesslayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datalayer.DatabaseConnection;

/**
 * AppData is a middleware designed to allow users interaction with the database
 * without allowing them to utilize the database directly.
 *
 */
public class AppData {

	/**
	 * The ArrayList of Person objects that is used to pass the data between the
	 * GUI and the database.
	 */
	private List<Person> people = new ArrayList<Person>();

	/**
	 * The AppData instance that must be created only once.
	 */
	private static AppData appData = null;

	/**
	 * Default constructor for AppData Object
	 */
	private AppData() {

	}

	/**
	 * Tests to see if the AppData has been initialized, and whether or not the
	 * necessary tables have been created in the database. If AppData is null,
	 * it creates the instance, and if either the database or the table are
	 * non-existent, it creates those as well.
	 * 
	 * @return The AppData Object being instantiated.
	 */
	public static AppData getAppData() {
		if (appData == null) {
			appData = new AppData();
		}

		try {
			DatabaseConnection.createDatabase("customers");
			DatabaseConnection.createTable("Person");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return appData;
	}

	/**
	 * Inserts a person into the first empty row with a unique identity key. The
	 * identity key is set by the database and is not alterable by the user.
	 * 
	 * @param person
	 *            The Person object being called
	 */
	public void insertPerson(Person person) {

		people.add(person);

		try {
			DatabaseConnection.insertPerson(person);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selects a row from the Person table in the database and creates a usable
	 * Person object in the application structure.
	 * 
	 * @param name
	 *            The data we wish to select from the database
	 * 
	 */
	public List<Person> selectPerson(String name) {

		people.clear();

		try {
			for (Person person : DatabaseConnection.selectPerson(name)) {
				people.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return people;
	}

	/**
	 * Creates a usable Array List of Person Objects from the Person table in
	 * the database.
	 * 
	 */
	public List<Person> findAllPeople() {

		people.clear();

		try {
			for (Person person : DatabaseConnection.findAllPeople()) {
				people.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return people;
	}

	/**
	 * Deletes a Person Object from the database by the row ID provided
	 * 
	 * @param id
	 *            The distinct ID number of the record being deleted.
	 */
	public String deletePerson(int id) {
		String message = "";
		try {
			message = DatabaseConnection.deletePerson(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return message;
	}

}