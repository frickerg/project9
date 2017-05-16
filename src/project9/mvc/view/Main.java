package project9.mvc.view;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import project9.mvc.controller.Controller;
import project9.mvc.controller.DataHandler;
import project9.mvc.model.Person;
import project9.mvc.model.Search;

public class Main extends Application {

    static final ArrayList<Person> customerList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
	final Search search = new Search(DataHandler.fillData());
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
}
