package com.rohith.springreactive.fluxandmono;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

/**
 * This is a test class for hot and cold publisher
 * 
 * @author vazha
 *
 */
public class HotAndColdPublisherTest {

	/**
	 * This is a method to test the cold publisher
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void coldPublisherTest() throws InterruptedException {
		Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1));
		stringFlux.subscribe(s -> System.out.println("SubscriberCold 1 is :" + s)); // emits the value from beginning
		Thread.sleep(2000);
		stringFlux.subscribe(s -> System.out.println("SubscriberCold 2 is : " + s)); // again emits the value from
																						// beginning
		Thread.sleep(4000);
	}

	/**
	 * This is a method to test the hot publisher
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void hotPublisherTest() throws InterruptedException {
		Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1));
		ConnectableFlux<String> connectableFlux = stringFlux.publish();
		connectableFlux.connect();
		connectableFlux.subscribe(s -> System.out.println("SubscriberHot 1 is :" + s));
		Thread.sleep(3000);
		connectableFlux.subscribe(s -> System.out.println("SubscriberHot 2 is :" + s)); // does not emit value from
																						// beginning
		Thread.sleep(4000);
	}
}
