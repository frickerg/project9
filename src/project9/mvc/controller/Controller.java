package project9.mvc.controller;

import project9.mvc.model.Person;
import project9.mvc.model.Search;

public class Controller {

    private final Search search;

    public Controller(Search search) {
	this.search = search;
    }

    public void clear() {
	this.search.clearSearch();
    }

    public void fill() {
	this.search.fillList();
    }

    public void limit(int value) {
	this.search.limit(value);
    }

    public void filter(String filter, String value) {
	this.search.filter(filter, value);
    }

    public void skip(int value) {
	this.search.skip(value);
    }

    public int count() {
	return this.search.count();
    }

    public Person find(String text) {
	return this.search.find(text);
    }
}
