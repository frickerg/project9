package project9.examples;

import java.util.List;

import project9.classes.SeededStream;

public class MainSeed {

    public static void main(String[] args) {
	final SeededStream<Integer> seed = new SeededStream<Integer>(1, x -> x + 1, x -> x <= 10);

	System.out.println(seed.countAll());
	System.out.println(seed.countAll());

	System.out.println(seed.filter(x -> x % 2 == 0).countAll());

	final List<Integer> seeds = seed.filter(x -> x % 2 == 0).toList();

	for (final Integer e : seeds) {
	    System.out.println(e.intValue());
	}

    }
}
