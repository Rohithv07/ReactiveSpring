package com.rohith.repository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rohith.domain.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PersonRepositoryImplTest {

	PersonRepositoryImpl personRepository;

	@BeforeEach
	void setUp() throws Exception {
		personRepository = new PersonRepositoryImpl();
	}

	@Test
	void testGetById() {
		Mono<Person> personMono = personRepository.getById(2);
		Person person = personMono.block();
		System.out.println(person.toString());
	}

	@Test
	void getByIdSubscribe() {
		Mono<Person> personMono = personRepository.getById(1);
		StepVerifier.create(personMono).expectNextCount(1).verifyComplete();
		personMono.subscribe(person -> {
			System.out.println(person.toString());
		});
	}
	
	@Test
	void getByIdSubscribeNotFound() {
		Mono<Person> personMono = personRepository.getById(9);
		StepVerifier.create(personMono).verifyComplete();
		personMono.subscribe(person -> {
			System.out.println(person.toString());
		});
	}

	@Test
	void getByIdMapFunction() {
		Mono<Person> personMono = personRepository.getById(1);
		personMono.map(person -> {
			System.out.println(person.toString());
			return person.getFirstName();
		}).subscribe(firstName -> {
			System.out.println(firstName);
		});
	}

	@Test
	void fluxTestBlockFirst() {
		Flux<Person> personFlux = personRepository.findAll();
		Person person = personFlux.blockFirst();
		System.out.println(person.toString());
	}

	@Test
	void fluxTestSubscribe() {
		Flux<Person> personFlux = personRepository.findAll();
		StepVerifier.create(personFlux).expectNextCount(4).verifyComplete();
		personFlux.subscribe(person -> {
			System.out.println(person.toString());
		});
	}

	@Test
	void fluxTestListMono() {
		Flux<Person> personFlux = personRepository.findAll();
		Mono<List<Person>> personListMono = personFlux.collectList();
		personListMono.subscribe(list -> {
			list.forEach(person -> {
				System.out.println(person.toString());
			});
		});
	}

	@Test
	void testFindPersonById() {
		Flux<Person> personFlux = personRepository.findAll();

		final Integer id = 3;

		Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).next();
		personMono.subscribe(person -> {
			System.out.println(person.toString());
		});

	}

	@Test
	void testFindPersonById_NotFound() {
		Flux<Person> personFlux = personRepository.findAll();

		final Integer id = 7;

		Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).next();
		personMono.subscribe(person -> {
			System.out.println(person.toString());
		});

	}
	
	@Test
	void testFindPersonById_NotFoundNext() {
		Flux<Person> personFlux = personRepository.findAll();

		final Integer id = 7;

		Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).next();
		personMono.doOnError(throwable -> {
			System.out.println("No user with id" + id);
		}).subscribe(person -> {
			System.out.println(person.toString());
		});

	}

	@Test
	void testFindPersonById_NotFoundWithException() {
		Flux<Person> personFlux = personRepository.findAll();

		final Integer id = 7;

		Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single(); // single signals for no
																								// such element
		personMono.doOnError(throwable -> {
			System.out.println("Not found, No user with id " + id);
		}).subscribe(person -> {
			System.out.println(person.toString());
		});

	}

	@Test
	void testFindPersonById_NotFoundWithExceptionMessage() {
		Flux<Person> personFlux = personRepository.findAll();

		final Integer id = 7;

		Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single(); // single signals for no
																								// such element
		personMono.doOnError(throwable -> {
			System.out.println("Not found, No user with id " + id);
		}).onErrorReturn(new Person()).subscribe(person -> {
			System.out.println(person.toString());
		});

	}

}
