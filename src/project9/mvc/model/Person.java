package project9.mvc.model;

import java.util.Calendar;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name, surname, date, gender, drugName;

    public Person(Integer id, String name, String surname, String date, String gender, String drugName) {
	this.id = new SimpleIntegerProperty(id);
	this.name = new SimpleStringProperty(name);
	this.surname = new SimpleStringProperty(surname);
	this.date = new SimpleStringProperty(date);
	this.gender = new SimpleStringProperty(gender);
	this.drugName = new SimpleStringProperty(drugName);
    }

    /**
     * @return the id
     */
    public Integer getId() {
	return this.id.get();
    }

    /**
     * @return the name
     */
    public String getName() {
	return this.name.get();
    }

    /**
     * @return the surname
     */
    public String getSurname() {
	return this.surname.get();
    }

    /**
     * @return the date
     */
    public String getDate() {
	return getAge() + "";
    }

    /**
     * @return the gender
     */
    public String getGender() {
	return this.gender.get();
    }

    /**
     * @return the drugName
     */
    public String getDrugName() {
	return this.drugName.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return this.name.get() + " " + this.surname.get();
    }

    public String getFullname() {
	return this.name.get() + " " + this.surname.get();
    }

    /**
     * @return the age
     */
    public double getAge() {
	final String date[] = this.date.get().split("/");
	final int born = Integer.parseInt(date[2]);
	return Calendar.getInstance().get(Calendar.YEAR) - born;
    }

}
