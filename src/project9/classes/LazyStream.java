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
	    final E element = iterator.next();
	    if (!predicate.test(element)) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public boolean matchAny(Predicate<? super E> predicate) {
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    final E element = iterator.next();
	    if (predicate.test(element)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int countAll() {
	final Iterator<E> iterator = iterator();
	int count = 0;
	while (iterator.hasNext()) {
	    iterator.next();
	    count++;
	}
	return count;
    }

    @Override
    public int count(Predicate<? super E> predicate) {
	final Iterator<E> iterator = iterator();
	int count = 0;
	while (iterator.hasNext()) {
	    if (predicate.test(iterator.next())) {
		count++;
	    }
	}
	return count;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
	final Iterator<E> iterator = iterator();
	if (index < 0) {
	    throw new IllegalArgumentException();
	}
	for (int n = 0; n < index; n++) {
	    iterator.next();
	}
	return iterator.next();
    }

    @Override
    public E find(Predicate<? super E> predicate) {
	final Iterator<E> iterator = iterator();
	while (iterator.hasNext()) {
	    final E current = iterator.next();
	    if (predicate.test(current)) {
		return current;
	    }
	}
	return null;
    }

    @Override
    public E reduce(Operator<E> operator) {
	final Iterator<E> iterator = iterator();
	if (!iterator.hasNext()) {
	    return null;
	}

	E temp = iterator.next();
	E result = temp;

	while (iterator.hasNext()) {
	    result = operator.apply(temp, iterator.next());
	    temp = result;
	}

	return result;
    }

    @Override
    public List<E> toList() {
	final Iterator<E> iterator = iterator();
	final List<E> list = new ArrayList<E>();
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
	int index = 0;
	final Iterator<E> iterator = iterator();

	while (index < n && iterator.hasNext()) {
	    iterator.next();
	    index++;
	    if (!iterator.hasNext()) {
		index = 0;
	    }
	}
	return new LazyStream<E>() {
	    @Override
	    public Iterator<E> iterator() {
		return iterator;
	    }
	};
    }

    @Override
    public Stream<E> filter(Predicate<? super E> predicate) {
	final Iterator<E> iterator = iterator();
	final List<E> limitedList = new ArrayList<E>();

	while (iterator.hasNext()) {
	    final E temp = iterator.next();
	    if (predicate.test(temp)) {
		limitedList.add(temp);
	    }
	}

	return new LazyStream<E>() {
	    @Override
	    public Iterator<E> iterator() {
		return new Iterator<E>() {
		    private int index = 0;

		    @Override
		    public boolean hasNext() {
			return this.index < limitedList.size();
		    }

		    @Override
		    public E next() {
			return limitedList.get(this.index++);
		    }
		};
	    }
	};
    }

    @Override
    public <F> Stream<F> map(Mapping<? super E, ? extends F> mapping) {
	final Iterator<E> iterator = iterator();
	final List<F> limitedList = new ArrayList<F>();

	while (iterator.hasNext()) {
	    final F temp = mapping.apply(iterator.next());
	    limitedList.add(temp);
	}
	return new LazyStream<F>() {
	    @Override
	    public Iterator<F> iterator() {
		return new Iterator<F>() {
		    private int index = 0;

		    @Override
		    public boolean hasNext() {
			return this.index < limitedList.size();
		    }

		    @Override
		    public F next() {
			return limitedList.get(this.index++);
		    }
		};
	    }
	};

    }

}
