package project9.interfaces;

@FunctionalInterface
public interface Mapping<E, F> {

	public F apply(E element);

}
