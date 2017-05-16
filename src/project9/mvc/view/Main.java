package project9.mvc.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import project9.mvc.controller.Controller;
import project9.mvc.model.Person;
import project9.mvc.model.Search;

public class Main extends Application {

    static final ArrayList<Person> customerList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
	final Search search = new Search(fillData());
	final Controller controller = new Controller(search);

	final TableGui dataTable = new TableGui(search);
	dataTable.setTitle("Customer List");
	dataTable.show();

	final ControllGui actions = new ControllGui(controller);
	actions.setTitle("Controller");
	actions.show();

    }

    public static void main(String[] args) throws FileNotFoundException {
	launch(args);
    }

    private static List<Person> fillData() throws FileNotFoundException {
	final List<Person> customerList = new ArrayList<>();

	final Scanner scan = new Scanner(new File("customerList.csv"));
	scan.nextLine();

	while (scan.hasNextLine()) {
	    final String customer = scan.nextLine();
	    final String[] personData = customer.split(",");
	    customerList.add(new Person(Integer.parseInt(personData[0]), personData[1], personData[2], personData[3],
		    personData[4], personData[5]));
	}
	scan.close();

	return customerList;
    }
}
