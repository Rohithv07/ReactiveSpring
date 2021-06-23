package com.rohith.springreactive.fluxandmono;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * This is a test class for testing flux and mono with time
 * 
 * @author vazha
 *
 */
public class FluxAndMonoWithTimeTest {

	/**
	 * This is a test method for testing infinite sequence
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void infiniteSequence() throws InterruptedException {
		Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200)).log();
		infiniteFlux.subscribe((element) -> System.out.println("Value is :" + element));
		Thread.sleep(3000);
	}

	/**
	 * This is a test method for testing infinite sequence
	 * 
	 */
	@Test
	public void infiniteSequenceTest() throws InterruptedException {
		Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(200)).take(3).log();
		StepVerifier.create(finiteFlux).expectSubscription().expectNext(0L, 1L, 2L).verifyComplete();
	}

	/**
	 * This is a test method for testing infinite sequence using map
	 * 
	 */
	@Test
	public void infiniteSequenceMapTest() throws InterruptedException {
		Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(200)).map(l -> new Integer(l.intValue())).take(3)
				.log();
		StepVerifier.create(finiteFlux).expectSubscription().expectNext(0, 1, 2).verifyComplete();
	}

	/**
	 * This is a test method for testing infinite sequence using map and delay
	 * 
	 */
	@Test
	public void infiniteSequenceMapWithDelayTest() throws InterruptedException {
		Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(200)).delayElements(Duration.ofSeconds(1))
				.map(l -> new Integer(l.intValue())).take(3).log();
		StepVerifier.create(finiteFlux).expectSubscription().expectNext(0, 1, 2).verifyComplete();
	}
}
