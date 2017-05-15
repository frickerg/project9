package project9.classes;

import java.util.Iterator;

import project9.interfaces.Mapping;
import project9.interfaces.Predicate;

public class SeededStream<E> extends LazyStream<E> {
    private final E seed;
    private E currentSeed;
    private final Mapping<E, E> update;
    private Predicate<E> condition = new Predicate<E>() {
	@Override
	public boolean test(E element) {
	    return true;
	}
    };

    public SeededStream(E seed, Mapping<E, E> update) {
	this.seed = seed;
	this.currentSeed = seed;
	this.update = update;
    }

    public SeededStream(E seed, Mapping<E, E> update, Predicate<E> condition) {
	this(seed, update);
	this.condition = condition;
    }

    @Override
    public Iterator<E> iterator() {
	this.currentSeed = this.seed;
	return new Iterator<E>() {
	    int limiter = 1024;
	    int index = 0;

	    @Override
	    public boolean hasNext() {
		final boolean hasNext = SeededStream.this.condition.test(SeededStream.this.currentSeed);
		if (!hasNext || this.index >= this.limiter) {
		    return false;
		}
		return hasNext;
	    }

	    @Override
	    public E next() {
		final E temp = SeededStream.this.currentSeed;
		SeededStream.this.currentSeed = SeededStream.this.update.apply(SeededStream.this.currentSeed);
		this.index++;
		return temp;
	    }
	};
    }

}