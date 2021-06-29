package com.rohith.springreactive.fluxandmono;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

/**
 * This is test class for testing the virtual time
 * 
 * @author vazha
 *
 */
public class VirtualTimeTest {

	/**
	 * This test method do the testing without virtual time
	 */
	@Test
	public void testingWithoutVirtualTime() {
		Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1)).take(3);
		StepVerifier.create(longFlux).expectSubscription().expectNext(0l, 1l, 2l).verifyComplete();
	}

	/**
	 * This test method do the testing with virtual time scheduler
	 */
	@Test
	public void testingWithVirtualTime() {
		VirtualTimeScheduler.getOrSet();
		Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1)).take(3);
		StepVerifier.withVirtualTime(() -> longFlux.log()).expectSubscription().thenAwait(Duration.ofSeconds(3))
				.expectNext(0l, 1l, 2l).verifyComplete();

	}
}
