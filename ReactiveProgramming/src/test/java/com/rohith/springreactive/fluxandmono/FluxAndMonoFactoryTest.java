package com.rohith.springreactive.fluxandmono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * This is a test class to test the flux and mono factory methods
 * 
 * @author vazha
 *
 */
public class FluxAndMonoFactoryTest {
	List<String> names = Arrays.asList("adam", "anna", "jack", "jenny");

	/**
	 * This is a test method for testing the flux based on a list
	 */
	@Test
	public void fluxUsingIterable() {
		Flux<String> namesFlux = Flux.fromIterable(names).log();
		StepVerifier.create(namesFlux).expectNext("adam", "anna", "jack", "jenny").verifyComplete();
	}

	/**
	 * This is a test method for testing the flux based on array
	 */
	@Test
	public void fluxUsingArray() {
		String[] names = new String[] { "adam", "anna", "jack", "jenny" };
		Flux<String> fluxArrayNames = Flux.fromArray(names).log();
		StepVerifier.create(fluxArrayNames).expectNext("adam", "anna", "jack", "jenny").verifyComplete();
	}

	/**
	 * This is a test method for testing the flux based on streams
	 */
	@Test
	public void fluxUsingStream() {
		Flux<String> fluxArrayStream = Flux.fromStream(names.stream()).log();
		StepVerifier.create(fluxArrayStream).expectNext("adam", "anna", "jack", "jenny").verifyComplete();
	}

	/**
	 * This is a test method to check for just or empty
	 */
	@Test
	public void monoUsingJustOrEmpty() {
		Mono<Object> mono = Mono.justOrEmpty(null).log(); // Mono.Empty()
		StepVerifier.create(mono.log()).verifyComplete();
	}

	/**
	 * This is a test method to check for mono using supplier
	 */
	@Test
	public void monoUsingSupplier() {
		Supplier<String> stringSupplier = () -> "adam";
		Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
		System.out.println(stringSupplier.get());
		StepVerifier.create(stringMono.log()).expectNext("adam").verifyComplete();
	}
	
	/**
	 * This is a test method to check for the flux for ranges
	 */
	@Test
	public void fluxUsingRange() {
		Flux<Integer> fluxRange = Flux.range(1, 5);
		StepVerifier.create(fluxRange).expectNext(1, 2, 3, 4, 5).verifyComplete();
	}
}
