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
	final Iterator<E> it = iterator();
	while (it.hasNext()) {
	    if (!predicate.test(it.next())) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public boolean matchAny(Predicate<? super E> predicate) {
	final Iterator<E> it = iterator();
	while (it.hasNext()) {
	    if (predicate.test(it.next())) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int countAll() {
	int count = 0;
	final Iterator<E> it = iterator();
	while (it.hasNext()) {
	    it.next();
	    count++;
	}
	return count;
    }

    @Override
    public int count(Predicate<? super E> predicate) {
	int count = 0;
	final Iterator<E> it = iterator();
	while (it.hasNext()) {
	    if (predicate.test(it.next())) {
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
	final Iterator<E> it = iterator();
	while (it.hasNext()) {
	    if (count == index) {
		return it.next();
	    }
	    it.next();
	    count++;
	}
	throw new IndexOutOfBoundsException();
    }

    @Override
    public E find(Predicate<? super E> predicate) {
	final Iterator<E> it = iterator();
	while (it.hasNext()) {
	    final E e = it.next();
	    if (predicate.test(e)) {
		return e;
	    }
	}
	return null;
    }

    @Override
    public E reduce(Operator<E> operator) {
	final Iterator<E> it = iterator();
	if (it.hasNext()) {
	    E value = it.next();
	    while (it.hasNext()) {
		value = operator.apply(value, it.next());
	    }
	    return value;
	}
	return null;

    }

    @Override
    public List<E> toList() {
	final List<E> list = new ArrayList<>();
	final Iterator<E> it = iterator();
	while (it.hasNext()) {
	    list.add(it.next());
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
		    Iterator<E> it = source.iterator();

		    @Override
		    public boolean hasNext() {
			return count < n && this.it.hasNext();
		    }

		    @Override
		    public E next() {
			count++;
			return this.it.next();
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

		    private final Iterator<E> it = source.iterator();

		    @Override
		    public boolean hasNext() {
			while (count < n && this.it.hasNext()) {
			    this.it.next();
			    count++;
			}
			return this.it.hasNext();

		    }

		    @Override
		    public E next() {
			return this.it.next();
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

		    private final Iterator<E> it = source.iterator();
		    private E element;

		    @Override
		    public boolean hasNext() {
			while (this.it.hasNext()) {
			    this.element = this.it.next();
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

		    private final Iterator<E> it = source.iterator();

		    @Override
		    public boolean hasNext() {
			return this.it.hasNext();
		    }

		    @Override
		    public F next() {
			return mapping.apply(this.it.next());
		    }
		};
	    }
	};
    }
}
