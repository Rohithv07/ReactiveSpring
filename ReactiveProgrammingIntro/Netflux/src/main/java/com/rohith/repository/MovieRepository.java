package com.rohith.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.rohith.domain.Movies;

public interface MovieRepository extends ReactiveMongoRepository<Movies, String> {

}
