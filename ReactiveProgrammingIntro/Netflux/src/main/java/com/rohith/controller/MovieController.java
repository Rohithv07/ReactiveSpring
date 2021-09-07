package com.rohith.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohith.domain.MovieEvent;
import com.rohith.domain.Movies;
import com.rohith.service.MovieService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;

	@GetMapping("/{id}")
	Mono<Movies> getMovieById(@PathVariable String id) {
		return movieService.getMovieById(id);
	}

	@GetMapping
	Flux<Movies> getAllMovies() {
		return movieService.getAllMovies();
	}

	@GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<MovieEvent> streamMovieEvent(@PathVariable String id) {
		return movieService.streamMovieEvent(id);
	}
}
