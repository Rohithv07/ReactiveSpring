package com.rohith.springreactive.fluxandmono;

import static reactor.core.scheduler.Schedulers.parallel;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * This is a test class for testing the transform reactive stream using map
 * 
 * @author vazha
 *
 */
public class FluxAndTransformTest {

	List<String> names = Arrays.asList("adam", "anna", "jack", "jenny");

	/**
	 * This is a test method to check for transform using map and converting string
	 * to upper case
	 */
	@Test
	public void transfromUsingMapToUppercase() {
		Flux<String> nameFlux = Flux.fromIterable(names).map(s -> s.toUpperCase()).log();
		StepVerifier.create(nameFlux).expectNext("ADAM", "ANNA", "JACK", "JENNY").verifyComplete();
	}

	/**
	 * This is a test method to check for transform using map and accessing only the
	 * length of strings.
	 */
	@Test
	public void transfromUsingMapLength() {
		Flux<Integer> nameFlux = Flux.fromIterable(names).map(s -> s.length()).log();
		StepVerifier.create(nameFlux).expectNext(4, 4, 4, 5).verifyComplete();
	}

	/**
	 * This is a test method to check for transform using map and repeating the
	 * length 5 times
	 */
	@Test
	public void transfromUsingMapLengthRepeat() {
		Flux<Integer> nameFlux = Flux.fromIterable(names).map(s -> s.length()).repeat(5).log();
		StepVerifier.create(nameFlux).expectNext(4, 4, 4, 5, 4, 4, 4, 5, 4, 4, 4, 5, 4, 4, 4, 5, 4, 4, 4, 5, 4, 4, 4, 5)
				.verifyComplete();
	}

	/**
	 * This is a test method to check for transform using map, filter and repeating
	 * the length 5 times
	 */
	@Test
	public void transfromUsingMapLengthRepeatCombineFilter() {
		Flux<Integer> nameFlux = Flux.fromIterable(names).filter(s -> s.length() > 4).map(s -> s.length()).repeat(5)
				.log(); // this is called pipeline
		StepVerifier.create(nameFlux).expectNext(5, 5, 5, 5, 5, 5).verifyComplete();
	}

	/**
	 * This is a test method to test the transform using a flatmap
	 */
	@Test
	public void transformUsingFlatMap() {
		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).flatMap(s -> {
			return Flux.fromIterable(convertToList(s));// A -> List[A, newValue], B-> List[B, newValue] ........
		}).log();
		StepVerifier.create(stringFlux).expectNextCount(12).verifyComplete();
	}

	/**
	 * This is a helper method used for the method transformUsingFlatMap method
	 * which delays the result by 1s
	 * 
	 * @param s
	 * @return List &ltString&gt {@link} transformUsingFlatMap
	 */
	private List<String> convertToList(String s) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.asList(s, "newValue");
	}

	/**
	 * This is a test method to test the transform using a flat map and functions
	 * parallel way
	 */
	@Test
	public void transformUsingFlatMapUsingParallel() {
		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2)
				.flatMap((s) -> s.map(this::convertToList).subscribeOn(parallel())).flatMap(s -> Flux.fromIterable(s))
				.log();
		StepVerifier.create(stringFlux).expectNextCount(12).verifyComplete();
	}

	/**
	 * This is a test method to test the transform using a flat map and functions
	 * parallel way maintaining order
	 */
	@Test
	public void transformUsingFlatMapUsingParallelMaintainingOrder() {
		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2)
				.concatMap((s) -> s.map(this::convertToList).subscribeOn(parallel())).flatMap(s -> Flux.fromIterable(s))
				.log();
		StepVerifier.create(stringFlux).expectNextCount(12).verifyComplete();
	}

	/**
	 * This is a test method to test the transform using a flat map and functions
	 * parallel way maintaining order
	 */
	@Test
	public void transformUsingFlatMapUsingParallelMaintainingOrderSequential() {
		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2)
				.flatMapSequential((s) -> s.map(this::convertToList).subscribeOn(parallel()))
				.flatMap(s -> Flux.fromIterable(s)).log();
		StepVerifier.create(stringFlux).expectNextCount(12).verifyComplete();
	}

}
