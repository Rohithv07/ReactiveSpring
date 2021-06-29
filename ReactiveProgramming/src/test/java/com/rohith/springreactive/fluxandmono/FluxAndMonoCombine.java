package com.rohith.springreactive.fluxandmono;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

/**
 * This is a class which tests for various combining operations and testing by
 * combining reactive streams
 * 
 * @author vazha
 *
 */
public class FluxAndMonoCombine {

	/**
	 * This test method test for the merging property of flux
	 */
	@Test
	public void combineUsingMerge() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.merge(flux1, flux2).log();
		StepVerifier.create(mergedFlux).expectSubscription().expectNext("A", "B", "C", "D", "E", "F").verifyComplete();
	}

	/**
	 * This test method test for the merging property with some delay
	 */
	@Test
	public void combineUsingMergeDelay() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
		Flux<String> mergedFlux = Flux.merge(flux1, flux2).log();
		StepVerifier.create(mergedFlux).expectSubscription().expectNextCount(6).verifyComplete();
	}

	/**
	 * This test method test for the merging property with some delay using virtula
	 * time scheduler
	 */
	@Test
	public void combineUsingMergeDelayVirtualTime() {
		VirtualTimeScheduler.getOrSet();
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
		Flux<String> mergedFlux = Flux.merge(flux1, flux2).log();
		StepVerifier.withVirtualTime(() -> mergedFlux.log()).expectSubscription().thenAwait(Duration.ofSeconds(6))
				.expectNextCount(6).verifyComplete();
	}

	/**
	 * This test method test for the concat property
	 */
	@Test
	public void combineUsingConcat() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.concat(flux1, flux2).log();
		StepVerifier.create(mergedFlux).expectSubscription().expectNext("A", "B", "C", "D", "E", "F").verifyComplete();
	}

	/**
	 * This test method test for the concat property with some delay
	 */
	@Test
	public void combineUsingConcatDelay() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
		Flux<String> mergedFlux = Flux.concat(flux1, flux2).log();
		StepVerifier.create(mergedFlux).expectSubscription().expectNext("A", "B", "C", "D", "E", "F").verifyComplete();
	}

	/**
	 * This test method test for the concat property with some delay using virtual
	 * time scheduler
	 */
	@Test
	public void combineUsingConcatDelayVirtualTimeScheduler() {
		VirtualTimeScheduler.getOrSet();
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
		Flux<String> mergedFlux = Flux.concat(flux1, flux2).log();
		StepVerifier.withVirtualTime(() -> mergedFlux.log()).expectSubscription().thenAwait(Duration.ofSeconds(6))
				.expectNextCount(6).verifyComplete();
	}

	/**
	 * This test method test for the zip property
	 */
	@Test
	public void combineUsingZip() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.zip(flux1, flux2, (t1, t2) -> {
			return t1.concat(t2);
		}).log(); // combination of first element from1 f1, f2. second element from f1, f2
		StepVerifier.create(mergedFlux).expectSubscription().expectNext("AD", "BE", "CF").verifyComplete();
	}
}
