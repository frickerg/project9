package project9.classes;

import java.util.Iterator;

public class ArrayStream<E> extends LazyStream<E> {

    private final E[] elements;

    public ArrayStream(E... elements) {
	this.elements = elements;
    }

    @Override
    public Iterator<E> iterator() {
	return new Iterator<E>() {

	    private int index = 0;

	    @Override
	    public boolean hasNext() {
		return this.index < ArrayStream.this.elements.length;
	    }

	    @Override
	    public E next() {
		return ArrayStream.this.elements[this.index++];
	    }
	};
    }

}
