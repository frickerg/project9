package project9.classes;

import java.util.Iterator;

import project9.interfaces.Mapping;
import project9.interfaces.Predicate;

public class SeededStream<E> extends LazyStream<E> {
    private E currentSeed;
    private Predicate<E> condition;
    private final E seed;
    private final Mapping<E, E> update;

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
	    @Override
	    public boolean hasNext() {
		if (SeededStream.this.condition == null) {
		    return true;
		}
		return SeededStream.this.condition.test(SeededStream.this.currentSeed);
	    }

	    @Override
	    public E next() {
		final E temp = SeededStream.this.currentSeed;
		SeededStream.this.currentSeed = SeededStream.this.update.apply(SeededStream.this.currentSeed);
		return temp;
	    }
	};
    }

}