package com.rohith.springreactive.fluxandmono;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * This is a test class for giving test for flux and mono filter
 * 
 * @author vazha
 *
 */
public class FluxAndMonoFilterTest {
	List<String> names = Arrays.asList("adam", "anna", "jack", "jenny");

	/**
	 * This is a test method for filter flux based on a starting letter
	 */
	@Test
	public void filterTestStartWithLetter() {
		Flux<String> namesFlux = Flux.fromIterable(names).filter(s -> s.startsWith("a")).log();
		StepVerifier.create(namesFlux).expectNext("adam", "anna").verifyComplete();
	}

	/**
	 * This is a test method for filter flux based on length of string
	 */
	@Test
	public void filterTestLength() {
		Flux<String> namesFlux = Flux.fromIterable(names).filter(s -> s.length() > 4).log();
		StepVerifier.create(namesFlux).expectNext("jenny").verifyComplete();
	}
}
