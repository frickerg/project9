package project9.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import project9.interfaces.Mapping;
import project9.interfaces.Operator;
import project9.interfaces.Predicate;
import project9.interfaces.Stream;

public abstract class LazyStream<E> implements Stream<E> {
    @Override
    public boolean matchAll(Predicate<? super E> predicate) {
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    if (!predicate.test(iterator.next())) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public boolean matchAny(Predicate<? super E> predicate) {
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    if (predicate.test(iterator.next())) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int countAll() {
	int count = 0;
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    iterator.next();
	    count++;
	}
	return count;
    }

    @Override
    public int count(Predicate<? super E> predicate) {
	int count = 0;
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    if (predicate.test(iterator.next())) {
		count++;
	    }
	}
	return count;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
	if (index < 0) {
	    throw new IndexOutOfBoundsException();
	}
	int count = 0;
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    if (count == index) {
		return iterator.next();
	    }
	    iterator.next();
	    count++;
	}
	throw new IndexOutOfBoundsException();
    }

    @Override
    public E find(Predicate<? super E> predicate) {
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    final E e = iterator.next();
	    if (predicate.test(e)) {
		return e;
	    }
	}
	return null;
    }

    @Override
    public E reduce(Operator<E> operator) {
	final Iterator<E> iterator = iterator();
	if (iterator.hasNext()) {
	    E value = iterator.next();
	    while (iterator.hasNext()) {
		value = operator.apply(value, iterator.next());
	    }
	    return value;
	}
	return null;

    }

    @Override
    public List<E> toList() {
	final List<E> list = new ArrayList<>();
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    list.add(iterator.next());
	}
	return list;
    }

    @Override
    public Stream<E> limit(int n) throws IllegalArgumentException {
	if (n < 0) {
	    throw new IllegalArgumentException();
	}
	final Stream<E> source = this;
	return new LazyStream<E>() {
	    private int count = 0;

	    @Override
	    public Iterator<E> iterator() {
		return new Iterator<E>() {
		    Iterator<E> iterator = source.iterator();

		    @Override
		    public boolean hasNext() {
			return count < n && this.iterator.hasNext();
		    }

		    @Override
		    public E next() {
			count++;
			return this.iterator.next();
		    }
		};
	    }
	};
    }

    @Override
    public Stream<E> skip(int n) throws IllegalArgumentException {
	if (n < 0) {
	    throw new IllegalArgumentException();
	}

	final Stream<E> source = this;

	return new LazyStream<E>() {

	    private int count = 0;

	    @Override
	    public Iterator<E> iterator() {
		return new Iterator<E>() {

		    private final Iterator<E> iterator = source.iterator();

		    @Override
		    public boolean hasNext() {
			while (count < n && this.iterator.hasNext()) {
			    this.iterator.next();
			    count++;
			}
			return this.iterator.hasNext();

		    }

		    @Override
		    public E next() {
			return this.iterator.next();
		    }

		};
	    }
	};
    }

    @Override
    public Stream<E> filter(Predicate<? super E> predicate) {
	final Stream<E> source = this;

	return new LazyStream<E>() {

	    @Override
	    public Iterator<E> iterator() {
		return new Iterator<E>() {

		    private final Iterator<E> iterator = source.iterator();
		    private E element;

		    @Override
		    public boolean hasNext() {
			while (this.iterator.hasNext()) {
			    this.element = this.iterator.next();
			    if (predicate.test(this.element)) {
				return true;
			    }
			}
			return false;
		    }

		    @Override
		    public E next() {
			return this.element;
		    }
		};
	    }
	};
    }

    @Override
    public <F> Stream<F> map(Mapping<? super E, ? extends F> mapping) {
	final Stream<E> source = this;
	return new LazyStream<F>() {

	    @Override
	    public Iterator<F> iterator() {
		return new Iterator<F>() {

		    private final Iterator<E> iterator = source.iterator();

		    @Override
		    public boolean hasNext() {
			return this.iterator.hasNext();
		    }

		    @Override
		    public F next() {
			return mapping.apply(this.iterator.next());
		    }
		};
	    }
	};
    }
}