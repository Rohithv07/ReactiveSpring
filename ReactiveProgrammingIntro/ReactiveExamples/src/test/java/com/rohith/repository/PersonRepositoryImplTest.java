package com.rohith.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rohith.domain.Person;

import reactor.core.publisher.Mono;

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

}
