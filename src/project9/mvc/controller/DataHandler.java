package project9.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project9.mvc.model.Person;

public abstract class DataHandler {
    private static final String PATH = "project9.data.ser";
    private static final String SOURCE = "customerList.csv";
    final List<Person> customerList = new ArrayList<>();

    public static List<Person> fillData() {
	List<Person> data = new ArrayList<Person>();
	final File f = new File(DataHandler.PATH);
	if (f.exists()) {
	    data = deserializeData();
	} else {
	    System.out.println("The data of " + DataHandler.SOURCE + " has not been serialized yet");
	    data = createData();
	    serializeData(data);
	}
	for (final Person entity : data) {
	    System.out.println(entity.toString());
	}
	return data;
    }

    @SuppressWarnings("unchecked")
    private static List<Person> deserializeData() {
	System.out.println("Loading serialized data from " + DataHandler.PATH);
	List<Person> data = new ArrayList<Person>();
	try {
	    final FileInputStream fileIn = new FileInputStream(DataHandler.PATH);
	    final ObjectInputStream in = new ObjectInputStream(fileIn);
	    data = (ArrayList<Person>) in.readObject();
	    in.close();
	    fileIn.close();
	} catch (final IOException | ClassNotFoundException e) {
	    e.printStackTrace();
	    System.exit(-1);
	}
	System.out.println("Amount of records : " + data.size());
	return data;
    }

    private static final void serializeData(List<Person> data) {
	try {
	    final FileOutputStream fileOut = new FileOutputStream(DataHandler.PATH);
	    final ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(data);
	    out.close();
	    fileOut.close();
	    System.out.printf("Serialized data is saved as " + DataHandler.PATH);
	} catch (final IOException i) {
	    i.printStackTrace();
	    System.exit(-1);
	}
    }

    private static List<Person> createData() {
	System.out.println("Creating data List from source " + DataHandler.SOURCE);
	final List<Person> data = new ArrayList<Person>();
	try (Scanner scan = new Scanner(new File(DataHandler.SOURCE));) {
	    scan.nextLine();

	    while (scan.hasNextLine()) {
		final String customer = scan.nextLine();
		final String[] personData = customer.split(",");
		data.add(new Person(Integer.parseInt(personData[0]), personData[1], personData[2], personData[3],
			personData[4], personData[5]));
	    }
	    scan.close();
	} catch (final FileNotFoundException e) {
	    e.printStackTrace();
	    System.exit(-1);
	}
	System.out.println("Amount of records : " + data.size());
	return data;
    }

}
