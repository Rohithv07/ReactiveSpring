package com.rohith.springreactive.fluxandmono;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * This is a test class to test for the back pressure
 * 
 * @author vazha
 *
 */
public class FluxAndMonoBackpressureTest {

	/**
	 * This is a test method for testing the backpressure
	 */
	@Test
	public void backPressureTest() {
		Flux<Integer> finiteFlux = Flux.range(1, 10).log();
		StepVerifier.create(finiteFlux).expectSubscription().thenRequest(1).expectNext(1).thenRequest(1).expectNext(2)
				.thenCancel().verify();
	}

	/**
	 * This is a test method which didn't do any particular testing but have a check
	 * on how back pressure is done
	 */
	@Test
	public void backPressure() {
		Flux<Integer> finiteFlux = Flux.range(1, 10).log();
		finiteFlux.subscribe((element) -> System.out.println("Element is : " + element),
				(e) -> System.err.println("Exception is : " + e), () -> System.out.println("Done"),
				(subscription -> subscription.request(2)));
	}

	/**
	 * This is a test method which didn't do any particular testing but have a check
	 * on how back pressure is done
	 */
	@Test
	public void backPressureCancel() {
		Flux<Integer> finiteFlux = Flux.range(1, 10).log();
		finiteFlux.subscribe((element) -> System.out.println("Element is : " + element),
				(e) -> System.err.println("Exception is : " + e), () -> System.out.println("Done"),
				(subscription -> subscription.cancel()));
	}

	/**
	 * This method shows customized back pressure
	 */
	@Test
	public void customizedBackPressure() {
		Flux<Integer> finiteFlux = Flux.range(1, 10).log();
		finiteFlux.subscribe(new BaseSubscriber<Integer>() {
			@Override
			protected void hookOnNext(Integer value) {
				request(1);
				System.out.println(value);
				if (value == 4) {
					cancel();
				}
			}
		});
	}
}
