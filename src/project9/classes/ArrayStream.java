package project9.classes;

import java.util.Iterator;

import lombok.Getter;

@Getter
public class ArrayStream<E> extends LazyStream<E> {
    private final E[] array;

    @SafeVarargs
    public ArrayStream(E... array) {
	this.array = array;
    }

    @Override
    public Iterator<E> iterator() {
	return new Iterator<E>() {
	    private int index = 0;

	    @Override
	    public boolean hasNext() {
		return this.index < ArrayStream.this.array.length;
	    }

	    @Override
	    public E next() {
		return ArrayStream.this.array[this.index++];
	    }
	};
    }
}
