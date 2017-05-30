package project9.mvc.view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project9.mvc.controller.Controller;
import project9.mvc.model.Person;

public class ControllGui extends Stage {
    private final Controller controller;

    private final BorderPane root = new BorderPane();
    private final VBox buttonVBox = new VBox(10);

    public ControllGui(Controller controller) {
	this.controller = controller;

	this.setScene(new Scene(this.root, 400, 400));
	this.setX(200);
	this.setY(700);
	intialise();
    }

    public void intialise() {
	this.root.setCenter(this.buttonVBox);
	this.buttonVBox.setPadding(new Insets(10, 10, 10, 10));

	final Button clearButton = new Button("clear");
	clearButton.addEventHandler(ActionEvent.ACTION, event -> {
	    this.controller.clear();
	});

	final Button fillButton = new Button("fill Data");
	fillButton.addEventHandler(ActionEvent.ACTION, event -> {
	    this.controller.fill();
	});

	final HBox hboxLimit = new HBox(10);
	final Button limitButton = new Button("limit");
	final TextField limitTextField = new TextField();
	limitButton.addEventHandler(ActionEvent.ACTION, event -> {
	    final int limit = Integer.parseInt(limitTextField.getText());
	    this.controller.limit(limit);
	    limitTextField.setText("");

	});
	hboxLimit.getChildren().addAll(limitTextField, limitButton);

	final HBox hBoxFilter = new HBox(10);
	final TextField filterTextField = new TextField();
	final ComboBox<String> personComboBox = new ComboBox<>();
	personComboBox.getItems().addAll("name", "surname", "age", "age >", "age <", "gender", "drugName");
	final Button filterButton = new Button("filter");
	filterButton.addEventHandler(ActionEvent.ACTION, event -> {
	    this.controller.filter(personComboBox.getValue(), filterTextField.getText());
	    filterTextField.setText("");
	});
	hBoxFilter.getChildren().addAll(filterTextField, personComboBox, filterButton);

	final HBox hBoxCount = new HBox(10);
	final Button countButton = new Button("count");
	final Label countLabel = new Label("");
	countButton.addEventHandler(ActionEvent.ACTION, event -> {
	    countLabel.setText(this.controller.count() + "");
	});
	hBoxCount.getChildren().addAll(countButton, countLabel);

	final HBox hBoxReset = new HBox(10);
	hBoxReset.getChildren().addAll(fillButton, clearButton);

	this.buttonVBox.getChildren().addAll(hboxLimit, hBoxFilter, hBoxCount, hBoxReset);

	final VBox vBoxSearch = new VBox(10);
	vBoxSearch.setPadding(new Insets(10, 10, 10, 10));

	final HBox hBoxSearch = new HBox(10);
	final TextField searchTextField = new TextField();
	final Button searchButton = new Button("search");
	final Label personLabel = new Label();
	searchButton.addEventHandler(ActionEvent.ACTION, event -> {
	    final String text = searchTextField.getText();
	    final Person person = this.controller.find(text);
	    if (person == null) {
		personLabel.setText("No customer found!!");
	    } else {
		personLabel.setText("Name: " + person.getFullname() + "\n" + "Age: " + person.getAge() + "\n"
			+ "Gender: " + person.getGender() + "\n" + "Drugname: " + person.getDrugName());
	    }
	});
	hBoxSearch.getChildren().addAll(searchTextField, searchButton);
	vBoxSearch.getChildren().addAll(hBoxSearch, personLabel);

	this.root.setBottom(vBoxSearch);

    }
}