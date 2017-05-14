package project9.interfaces;

@FunctionalInterface
public interface Predicate<E> {

	public boolean test(E element);

}
