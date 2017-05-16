package project9.mvc.view;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import project9.mvc.model.Person;
import project9.mvc.model.Search;

public class TableGui extends Stage implements Observer {
    private final Search search;

    private final GridPane root = new GridPane();
    private final TableView<Person> table = new TableView<>();
    private final ObservableList<Person> dataList = FXCollections.observableArrayList();

    public TableGui(Search search) {
	this.search = search;
	this.search.addObserver(this);
	this.dataList.addAll(search.getList());

	this.setScene(new Scene(this.root, 1200, 700));

	initialise();
    }

    public void initialise() {
	this.table.setEditable(true);
	this.table.setMinWidth(1200);
	this.table.setMinHeight(700);
	this.table.setPadding(new Insets(10, 10, 10, 10));

	// Set up TableView with colums
	// id
	final TableColumn<Person, Integer> id = new TableColumn<Person, Integer>("ID");
	id.setCellValueFactory(new PropertyValueFactory<>("id"));

	// name
	final TableColumn<Person, String> firstName = new TableColumn<Person, String>("First Name");
	firstName.setCellValueFactory(new PropertyValueFactory<>("name"));

	// surname
	final TableColumn<Person, String> secondName = new TableColumn<Person, String>("Second Name");
	secondName.setCellValueFactory(new PropertyValueFactory<>("surname"));

	// date of birth
	final TableColumn<Person, Integer> birthDate = new TableColumn<Person, Integer>("Age");
	birthDate.setCellValueFactory(new PropertyValueFactory<>("date"));

	// gender
	final TableColumn<Person, String> gender = new TableColumn<Person, String>("Gender");
	gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

	// drug name
	final TableColumn<Person, String> drugName = new TableColumn<Person, String>("Drug Name");
	drugName.setCellValueFactory(new PropertyValueFactory<>("drugName"));

	// Insert person data to table
	this.table.setItems(this.dataList);
	this.table.getColumns().add(id);
	this.table.getColumns().add(firstName);
	this.table.getColumns().add(secondName);
	this.table.getColumns().add(birthDate);
	this.table.getColumns().add(gender);
	this.table.getColumns().add(drugName);

	// Add table to the GridPane
	this.root.getChildren().add(this.table);
    }

    private void fillCustomerList() {
	this.table.getItems().clear();
	this.dataList.addAll(this.search.getList());
	this.table.setItems(this.dataList);
    }

    @Override
    public void update(Observable o, Object arg) {
	fillCustomerList();
    }

}
