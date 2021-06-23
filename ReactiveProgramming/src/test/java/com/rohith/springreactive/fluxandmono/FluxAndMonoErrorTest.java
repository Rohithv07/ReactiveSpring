package com.rohith.springreactive.fluxandmono;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * This is a test class which test for the various error handling
 * 
 * @author vazha
 *
 */
public class FluxAndMonoErrorTest {

	/**
	 * This method check for an error in flux
	 */
	@Test
	public void fluxErrorHandling() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D"));
		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C")
				.expectError(RuntimeException.class).verify();
	}

	/**
	 * This method for handling the error using onErrorResume
	 */
	@Test
	public void fluxErrorHandlingOnErrorResume() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D"))
				.onErrorResume((e) -> {
					System.out.println("Exception is : " + e);
					return Flux.just("Default");
				}); // this block is executed when there is an error
		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("Default")
				.verifyComplete();
	}

	/**
	 * This method for handling the error using onErrorReturn
	 */
	@Test
	public void fluxErrorHandlingOnErrorReturn() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D"))
				.onErrorReturn("Default");
		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("Default")
				.verifyComplete();
	}

	/**
	 * This method for handling the error using onErrorMap
	 */
	@Test
	public void fluxErrorHandlingOnErrorMap() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e));
		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C")
				.expectError(CustomException.class).verify();
	}

	/**
	 * This method for handling the error using retry operation 2 times
	 */
	@Test
	public void fluxErrorHandlingWithRetry() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e)).retry(2);
		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("A", "B", "C")
				.expectNext("A", "B", "C").expectError(CustomException.class).verify();
	}

	/**
	 * This method for handling the error using retry back-off operation
	 */
	@Test
	public void fluxErrorHandlingWithRetryBackOff() {
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e)).retryBackoff(2, Duration.ofSeconds(5));
		StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("A", "B", "C")
				.expectNext("A", "B", "C").expectError(IllegalStateException.class).verify();
	}

}
