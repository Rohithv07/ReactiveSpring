package com.rohith.springreactive.fluxandmono;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The test class for testing flux and mono
 * 
 * @author vazha
 *
 */
public class FluxAndMonoTest {

	/**
	 * The test method for testing flux
	 */
	@Test
	public void fluxTest() {
		/**
		 * This is a flux field of type string
		 */
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
		stringFlux.subscribe(System.out::println, (e) -> System.err.println(e), () -> System.out.println("Completed"));// there
																														// wont
																														// be
																														// completed
																														// as
																														// our
																														// flux
																														// terminated
																														// with
																														// error
		/**
		 * 10:38:36.168 [main] INFO reactor.Flux.ConcatArray.1 -
		 * onSubscribe(FluxConcatArray.ConcatArraySubscriber) 10:38:36.203 [main] INFO
		 * reactor.Flux.ConcatArray.1 - request(unbounded) 10:38:36.205 [main] INFO
		 * reactor.Flux.ConcatArray.1 - onNext(Spring) Spring 10:38:36.206 [main] INFO
		 * reactor.Flux.ConcatArray.1 - onNext(Spring Boot) Spring Boot 10:38:36.206
		 * [main] INFO reactor.Flux.ConcatArray.1 - onNext(Reactive Spring) Reactive
		 * Spring 10:38:36.208 [main] ERROR reactor.Flux.ConcatArray.1 -
		 * onError(java.lang.RuntimeException: Exception occured) 10:38:36.236 [main]
		 * ERROR reactor.Flux.ConcatArray.1 - java.lang.RuntimeException: Exception
		 * occured
		 */
	}

	/**
	 * The test method to test flux elements without error
	 */
	@Test
	public void fluxTestElementsWithoutError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring").log();
		StepVerifier.create(stringFlux).expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring")
				.verifyComplete();
	}

	/**
	 * The test method to test flux elements with error
	 */
	@Test
	public void fluxTestElementsWithError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
		StepVerifier.create(stringFlux).expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring")
				.expectError(RuntimeException.class).verify();
	}

	/**
	 * The test method to test flux elements with error for the error message
	 */
	@Test
	public void fluxTestElementsWithErrorForErrorMessage() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
		StepVerifier.create(stringFlux).expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring")
				.expectErrorMessage("Exception occured").verify();
	}

	/**
	 * The test method to test count of flux elements
	 */
	@Test
	public void fluxTestElementsCount() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
		StepVerifier.create(stringFlux).expectNextCount(3).expectErrorMessage("Exception occured").verify();
	}

	/**
	 * The test method to test flux elements with error writing all expect in single line
	 */
	@Test
	public void fluxTestElementsWithErrorSingleLineExpect() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
		StepVerifier.create(stringFlux).expectNext("Spring", "Spring Boot", "Reactive Spring")
				.expectError(RuntimeException.class).verify();
	}
	
	/**
	 * The test method is to test the mono type
	 */
	@Test
	public void monoTest() {
		Mono<String> stringMono = Mono.just("Spring");
		StepVerifier.create(stringMono.log()).expectNext("Spring").verifyComplete();
	}
	/**
	 * The test method is to test the mono type with error
	 */
	@Test
	public void monoTestWithError() {
		Mono<String> stringMono = Mono.error(new RuntimeException("Exception occured"));
		StepVerifier.create(stringMono.log()).expectError(RuntimeException.class).verify();
	}
}
