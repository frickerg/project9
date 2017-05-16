package project9.mvc.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Properties;;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Properties properties = new Properties();

    public Person(Integer id, String name, String surname, String date, String gender, String drugName) {
	this.properties.put("id", id.toString());
	this.properties.put("name", name);
	this.properties.put("surname", surname);
	this.properties.put("date", date);
	this.properties.put("gender", gender);
	this.properties.put("drugName", drugName);
    }

    /**
     * @return the id
     */
    public Integer getId() {
	return Integer.valueOf(this.properties.getProperty("id"));
    }

    /**
     * @return the name
     */
    public String getName() {
	return this.properties.getProperty("name");
    }

    /**
     * @return the surname
     */
    public String getSurname() {
	return this.properties.getProperty("surname");
    }

    /**
     * @return the date
     */
    public String getDate() {
	return Double.valueOf(getAge()).toString();
    }

    /**
     * @return the gender
     */
    public String getGender() {
	return this.properties.getProperty("gender");
    }

    /**
     * @return the drugName
     */
    public String getDrugName() {
	return this.properties.getProperty("drugName");
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return this.properties.toString();
    }

    public String getFullname() {
	return this.properties.getProperty("name") + " " + this.properties.get("surname");
    }

    /**
     * @return the age
     */
    public double getAge() {
	final String date[] = this.properties.get("date").toString().split("/");
	final int born = Integer.parseInt(date[2]);
	return Calendar.getInstance().get(Calendar.YEAR) - born;
    }

}
