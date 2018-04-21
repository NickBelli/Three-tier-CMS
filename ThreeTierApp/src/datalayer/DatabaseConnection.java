package datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import businesslayer.Person;

/**
 * Main Application Class to Create a new database with tables, and then insert
 * records, select records, create new objects from database records, and delete
 * records. Also allows a full printing of the table.
 * 
 *
 */
public class DatabaseConnection {

	/**
	 * The global connection variable.
	 */
	private static Connection conn;

	/**
	 * Method returns the connection to the database for use in the application
	 * so that multiple instances of the connection are not created and left
	 * open, the one instance is just opened and closed.
	 * 
	 * @return The connection that is needed for the task
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		if (conn == null || conn.isClosed()) {
			conn = createConnection();
		}
		return conn;
	}

	/**
	 * Creates the connection on the database using login info for the database
	 * itself. Uses the MySQL driver that must be included in the referenced
	 * library for the connection to work.
	 * 
	 * @return
	 */
	private static Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/customers?verifyServerCertificate=false&useSSL=true", "scott", "tiger");
			return connect;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Creates a database in MySQL if one does not already exist.
	 * 
	 * @param dbName
	 *            The name of the database being searched prior to creation.
	 * @return String message letting middleware know that the database exists.
	 * @throws SQLException
	 */
	public static String createDatabase(String dbName) throws SQLException {
		conn = getConnection();

		Statement statement = conn.createStatement();

		statement.executeUpdate("create database if not exists " + dbName);
		statement.executeUpdate("use " + dbName);

		statement.close();
		conn.close();
		return "Database has been created";

	}

	/**
	 * Creates a table in MySQL if one does not already exist. This is
	 * intentionally broken away from the database in case the database name
	 * needs to be altered independently of the table.
	 * 
	 * @param tableName
	 *            The name of the table being searched prior to creation.
	 * @return String message letting middleware know that the table exists.
	 * @throws SQLException
	 */
	public static String createTable(String tableName) throws SQLException {

		conn = getConnection();

		Statement statement = conn.createStatement();
		statement.executeUpdate(
				"create table if not exists Person(id MEDIUMINT NOT NULL AUTO_INCREMENT, name varchar(50) NOT NULL,"
						+ "email varchar(200) NOT NULL, " + " phoneNumber varchar(20) NOT NULL,"
						+ " PRIMARY KEY (id))");

		statement.close();
		conn.close();

		return "Table has been created";

	}

	/**
	 * Inserts a person into the first empty row with a unique identity key.
	 * 
	 * @param person
	 *            The Person object being called
	 * @param con
	 *            The database connection being passed from the Main
	 * @throws SQLException
	 */
	public static void insertPerson(Person person) throws SQLException {

		conn = getConnection();

		Statement stmt = null;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet mySet = stmt.executeQuery("SELECT * from Person");

		mySet.moveToInsertRow();
		mySet.updateString("name", person.getName());
		mySet.updateString("email", person.getEmail());
		mySet.updateString("phoneNumber", person.getPhone());

		mySet.insertRow();
		mySet.beforeFirst();

		mySet.close();
		stmt.close();
		conn.close();
	}

	/**
	 * Selects a row from the Person table in the database and creates a usable
	 * Person object array in the application structure.
	 * 
	 * @param firstName
	 *            is the data we wish to select from the database
	 * @param con
	 *            The database connection being passed from the Main
	 * @throws SQLException
	 */
	public static ArrayList<Person> selectPerson(String name) throws SQLException {

		conn = getConnection();
		ArrayList<Person> customerList = new ArrayList<>();

		Statement stmt = null;
		stmt = conn.createStatement();
		ResultSet mySet = stmt.executeQuery("SELECT * from Person where name LIKE '%" + name + "%';");
		while (mySet.next()) {
			Person person = new Person(mySet.getInt(1), mySet.getString(2), mySet.getString(3), mySet.getString(4));
			customerList.add(person);
		}

		mySet.close();
		stmt.close();
		conn.close();

		return customerList;
	}

	/**
	 * Creates a usable Array List of Person Objects from the Person table in
	 * the database.
	 * 
	 * @param con
	 *            The database connection being passed from the Main
	 * @return customerList the list of all Person Objects in the person table
	 *         of the customer database.
	 * @throws SQLException
	 */
	public static List<Person> findAllPeople() throws SQLException {

		conn = getConnection();

		ArrayList<Person> customerList = new ArrayList<>();
		Statement stmt = null;

		try {
			stmt = conn.createStatement();

			ResultSet mySet = stmt.executeQuery("SELECT * from Person");
			while (mySet.next()) {
				Person person = new Person(mySet.getInt(1), mySet.getString(2), mySet.getString(3), mySet.getString(4));
				customerList.add(person);
			}
			mySet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}

		conn.close();
		return customerList;
	}

	/**
	 * Deletes a Person Object from the database by the row ID provided
	 * 
	 * @param id
	 *            The distinct ID number of the record being deleted.
	 * @return message - Allows the user to know a record is being deleted or is
	 *         not found in the database.
	 * @throws SQLException
	 */
	public static String deletePerson(int id) throws SQLException {

		conn = getConnection();
		String message = "";
		Statement stmt = null;

		try {
			stmt = conn.createStatement();

			ResultSet mySet = stmt.executeQuery("SELECT * from Person where id = " + id);

			if (mySet.next()) {
				message = ("\nCustomer ID #" + id + " was found in the database and will be deleted.\n");
				stmt.executeUpdate("DELETE from Person where id = " + id);
			} else {
				message = ("\nCustomer ID #" + id + " was not found in the database.\n");
			}

			mySet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}

		conn.close();
		return message;
	}

}
