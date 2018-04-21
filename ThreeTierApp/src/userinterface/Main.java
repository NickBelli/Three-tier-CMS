package userinterface;

import java.sql.SQLException;
import businesslayer.AppData;
import businesslayer.InvalidTelephoneException;
import businesslayer.Person;
import businesslayer.TelephoneNumber;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The Main file is the originator of the GUI and the home of all
 * front end code functionality. It interacts with the AppData middleware
 * to allow data transfer to the DatabaseConnection file. It utilizes
 * JavaFX and contains three different screens. 
 * 
 * The first welcome screen allows a user to choose whether to enter data
 * or search for data. The second screen is the data entry screen, and the
 * third screen is the data search screen. The first two utilize gridpanes,
 * and the third screen utilizes a table for ease of reading the database objects
 * that are being returned. 
 * 
 *
 */
public class Main extends Application {

	private TableView<Person> table = new TableView<Person>();
	private final ObservableList<Person> data = FXCollections.observableArrayList();

	/**
	 * Overrides the JavaFX start and builds the screens and stages, allowing 
	 * button functionality as well. 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			/////////////////////////////////////////
			/*
			 * Welcome Screen Creation Create the GridPanes to show a welcome
			 * message and initial buttons for selection options
			 */
			/////////////////////////////////////////

			Button infoEntry = new Button("Enter Your Info");
			Button searchAndUpdate = new Button("Search and Update");

			GridPane welcome = new GridPane();

			welcome.add(new Label("Welcome to the Customer Data Entry Tool"), 1, 1);
			welcome.setAlignment(Pos.CENTER);
			welcome.setVgap(30.5);
			welcome.setHgap(10.5);

			GridPane selections = new GridPane();
			selections.setAlignment(Pos.CENTER);
			selections.setHgap(5.5);
			selections.add(infoEntry, 1, 0);
			selections.add(searchAndUpdate, 2, 0);

			BorderPane welcomeScreen = new BorderPane();
			welcomeScreen.setTop(welcome);
			welcomeScreen.setCenter(selections);
			Scene main = new Scene(welcomeScreen, 350, 350);
			primaryStage.setTitle("Welcome");
			primaryStage.setScene(main);
			primaryStage.show();

			/////////////////////////////////////////
			/*
			 * Information Entry Screen Creation Create the GridPane that will
			 * replace the Welcome Screen in the Application Window once the
			 * "Enter Your Info" button is pressed. Includes three buttons and
			 * three text areas for data entry.
			 */
			/////////////////////////////////////////

			Button okInfo = new Button("OK");
			Button cancelinfo = new Button("Cancel and Quit");
			Button backinfo = new Button("Back to Main");

			TextField infoName = new TextField();
			TextField infoEmail = new TextField();
			TextField infoPhone = new TextField();

			GridPane infoGUI = new GridPane();
			infoGUI.setAlignment(Pos.CENTER);
			infoGUI.setHgap(5.5);
			infoGUI.setVgap(5.5);

			infoGUI.add(new Label("Name: "), 0, 0);
			infoGUI.add(infoName, 1, 0);
			infoGUI.add(new Label("Email: "), 0, 1);
			infoGUI.add(infoEmail, 1, 1);
			infoGUI.add(new Label("Phone Number: "), 0, 2);
			infoGUI.add(infoPhone, 1, 2);

			GridPane okCancelInfo = new GridPane();
			okCancelInfo.setAlignment(Pos.TOP_RIGHT);
			okCancelInfo.setPadding(new Insets(0, 90, 100, 0));
			okCancelInfo.setHgap(10.5);
			okCancelInfo.add(okInfo, 0, 0);
			okCancelInfo.add(cancelinfo, 1, 0);
			okCancelInfo.add(backinfo, 2, 0);

			BorderPane infoScreen = new BorderPane();
			infoScreen.setCenter(infoGUI);
			infoScreen.setBottom(okCancelInfo);
			Scene info = new Scene(infoScreen, 350, 350);

			/////////////////////////////////////////
			/*
			 * Search Screen Creation Create the Table View that will replace
			 * the Welcome Screen in the Application Window once the
			 * "Search and Update" is pressed.
			 */
			/////////////////////////////////////////
			Scene search = new Scene(new Group(), 550, 550);

			final Label titlecustInfo = new Label("Customer Information");
			titlecustInfo.setFont(new Font("Arial", 20));

			table.setEditable(false);

			TableColumn<Person, String> idCol = new TableColumn<>("ID");
			idCol.setMinWidth(5);
			idCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));

			TableColumn<Person, String> nameCol = new TableColumn<>("Name");
			nameCol.setMinWidth(100);
			nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));

			TableColumn<Person, String> emailCol = new TableColumn<>("Email");
			emailCol.setMinWidth(200);
			emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

			TableColumn<Person, String> phoneCol = new TableColumn<>("Phone Number");
			phoneCol.setMinWidth(100);
			phoneCol.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

			table.setItems(data);
			table.getColumns().addAll(idCol, nameCol, emailCol, phoneCol);

			final TextField searchName = new TextField();
			searchName.setPromptText("Name");
			searchName.setMaxWidth(200);

			final TextField deleteID = new TextField();
			deleteID.setPromptText("Customer ID");
			deleteID.setMaxWidth(200);

			Button searchButton = new Button("Search");
			Button getAllRecords = new Button("Get All Customers");
			Button deleteButton = new Button("Delete");
			Button backSearch = new Button("Back to Main");
			Button quitSearch = new Button("Quit");

			final Label deleteConfirmation = new Label("The Customer has been deleted.");
			deleteConfirmation.setFont(new Font("Arial", 10));

			// final TextField searchEmail = new TextField();
			// searchEmail.setMaxWidth(emailCol.getPrefWidth());
			// searchEmail.setPromptText("Email");
			// final TextField searchPhone = new TextField();
			// searchPhone.setMaxWidth(emailCol.getPrefWidth());
			// searchPhone.setPromptText("Phone");

			final HBox searchBox = new HBox();
			searchBox.getChildren().addAll(searchName, searchButton, deleteID, deleteButton);
			searchBox.setSpacing(8);

			final HBox navigationBox = new HBox();
			navigationBox.getChildren().addAll(getAllRecords, backSearch, quitSearch);
			navigationBox.setSpacing(10);
			backSearch.setPrefSize(100, 20);
			quitSearch.setPrefSize(100, 20);

			final VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.setPadding(new Insets(10, 0, 100, 25));
			vbox.getChildren().addAll(titlecustInfo, table, searchBox, navigationBox);

			((Group) search.getRoot()).getChildren().addAll(vbox);

			// =============================//
			/*
			 * Button Creations for Program
			 */
			// =============================//

			/////////////////////////////////////////
			/*
			 * Welcome Page Buttons
			 */
			/////////////////////////////////////////
			infoEntry.setOnAction(e -> {
				System.out.println("The Enter Info button is pressed.");
				primaryStage.setTitle("Personal Info");
				primaryStage.setScene(info);
				infoName.clear();
				infoEmail.clear();
				infoPhone.clear();
			});

			searchAndUpdate.setOnAction(e -> {
				primaryStage.setTitle("Search and Update");
				primaryStage.setScene(search);
			});

			/////////////////////////////////////////
			/*
			 * Info Entry Page Buttons
			 */
			/////////////////////////////////////////

			okInfo.setOnAction(e -> {
				System.out.println("\nThe OK Button is pressed.");
				if (infoName.getText().length() == 0) {
					infoName.setText("Please enter your name");
				} else if (infoEmail.getText().length() == 0) {
					infoEmail.setText("Please enter your email");
				} else if (infoPhone.getText().length() == 0) {
					infoPhone.setText("Please enter your phone number");
				} else if (infoPhone.getText().length() > 0) {
					try {
						TelephoneNumber number = new TelephoneNumber(infoPhone.getText());
						Person person1 = new Person(infoName.getText(), infoEmail.getText(), number.toString());
						AppData.getAppData().insertPerson(person1);
						infoName.clear();
						infoEmail.clear();
						infoPhone.clear();
						infoGUI.add(new Label("Your information has \nbeen entered."), 0, 3);
					} catch (InvalidTelephoneException e1) {
						infoPhone.setText("Please enter a proper format");
					}

				}
			});

			cancelinfo.setOnAction(e -> {
				primaryStage.close();
			});

			backinfo.setOnAction(e -> {
				primaryStage.setScene(main);
			});

			/////////////////////////////////////////
			/*
			 * Search Page Buttons
			 */
			/////////////////////////////////////////

			/*
			 * Only search if a string is provided. 
			 * Search All functionality is its own button in case
			 * further permissions are sought for administration tasks.
			 */
			searchButton.setOnAction(e -> {
				data.clear();
				if (searchName.getText().length() > 0) {
					for (Person searchPerson : AppData.getAppData().selectPerson(searchName.getText())) {
						data.add(searchPerson);
					}
				} else {
					searchName.setText("Please enter a search value");
				}
			});

			deleteButton.setOnAction(e -> {
				int id = Integer.parseInt(deleteID.getText());
				if (id > 0) {
					System.out.println(AppData.getAppData().deletePerson(id));
					data.clear();
					for (Person findAll : AppData.getAppData().findAllPeople()) {
						data.add(findAll);
					}
					vbox.getChildren().add(deleteConfirmation);
				}
			});

			getAllRecords.setOnAction(e -> {
				data.clear();
				for (Person findAll : AppData.getAppData().findAllPeople()) {
					data.add(findAll);
				}
			});

			backSearch.setOnAction(e -> {
				primaryStage.setScene(main);
			});

			quitSearch.setOnAction(e -> {
				primaryStage.close();
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		launch(args);

	}
}
