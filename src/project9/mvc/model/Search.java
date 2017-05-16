package project9.mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import project9.classes.ArrayStream;

public class Search extends Observable {

    private final List<Person> customerList;
    private List<Person> currentList;
    private ArrayStream<Person> streamList;

    public Search(List<Person> customerList) {
	this.customerList = customerList;
	this.currentList = customerList;
	this.streamList = new ArrayStream<>(customerList.toArray(new Person[customerList.size()]));
    }

    public final void clearSearch() {
	this.currentList = new ArrayList<>();
	acctuallList(this.currentList);
	this.setChanged();
	this.notifyObservers();
    }

    public final void fillList() {
	this.currentList = this.customerList;
	acctuallList(this.currentList);
	this.setChanged();
	this.notifyObservers();
    }

    public final void limit(int value) {
	this.currentList = this.streamList.limit(value).toList();
	acctuallList(this.currentList);
	this.setChanged();
	this.notifyObservers();
    }

    public final void filter(String filter, String value) {
	switch (filter) {
	case "name":
	    this.currentList = this.streamList.filter(p -> p.getName().startsWith(value)).toList();
	    break;
	case "surname":
	    this.currentList = this.streamList.filter(p -> p.getSurname().startsWith(value)).toList();
	    break;
	case "age":
	    final int age = Integer.parseInt(value);
	    this.currentList = this.streamList.filter(p -> p.getAge() == age).toList();
	    break;
	case "age >":
	    final int ageBigger = Integer.parseInt(value);
	    this.currentList = this.streamList.filter(p -> p.getAge() > ageBigger).toList();
	    break;
	case "age <":
	    final int ageSmaller = Integer.parseInt(value);
	    this.currentList = this.streamList.filter(p -> p.getAge() < ageSmaller).toList();
	    break;
	case "gender":
	    this.currentList = this.streamList.filter(p -> p.getGender().equals(value)).toList();
	    break;
	case "drugName":
	    this.currentList = this.streamList.filter(p -> p.getDrugName().startsWith(value)).toList();
	    break;
	}
	acctuallList(this.currentList);
	setChanged();
	notifyObservers();
    }

    public final void skip(int value) {
	this.currentList = this.streamList.skip(value).toList();
	acctuallList(this.currentList);
	this.setChanged();
	this.notifyObservers();
    }

    public List<Person> getList() {
	return this.currentList;
    }

    private void acctuallList(List<Person> customerList) {
	this.streamList = new ArrayStream<>(customerList.toArray(new Person[customerList.size()]));
    }

    public int count() {
	return this.streamList.countAll();
    }

    public Person find(String text) {
	final Person person = this.streamList.find(p -> p.getFullname().equals(text));
	return person;
    }

}
