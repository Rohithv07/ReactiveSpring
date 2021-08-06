package com.rohith.repository;

import com.rohith.domain.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

	Person michael = new Person(1, "Michael", "Weston");
	Person fiona = new Person(2, "Fiona", "Glen");
	Person sam = new Person(3, "Sam", "Axe");
	Person jessy = new Person(4, "Jessy", "Pinkman");

	@Override
	public Mono<Person> getById(Integer id) {
		return Mono.just(jessy);
	}

	@Override
	public Flux<Person> findAll() {
		return Flux.just(michael, fiona, sam, jessy);
	}

}
