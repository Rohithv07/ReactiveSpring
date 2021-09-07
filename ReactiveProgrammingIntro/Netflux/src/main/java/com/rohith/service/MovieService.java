package com.rohith.service;

import com.rohith.domain.MovieEvent;
import com.rohith.domain.Movies;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

	Mono<Movies> getMovieById(String id);

	Flux<Movies> getAllMovies();
	
	Flux<MovieEvent> streamMovieEvent(String id);

}
