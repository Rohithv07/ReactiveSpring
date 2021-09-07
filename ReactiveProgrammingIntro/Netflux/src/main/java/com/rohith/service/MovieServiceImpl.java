package com.rohith.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.rohith.domain.MovieEvent;
import com.rohith.domain.Movies;
import com.rohith.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;

	@Override
	public Mono<Movies> getMovieById(String id) {
		return movieRepository.findById(id);
	}

	@Override
	public Flux<Movies> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Flux<MovieEvent> streamMovieEvent(String id) {
		return Flux.<MovieEvent>generate(movieEventSynchronousSink -> {
			movieEventSynchronousSink.next(new MovieEvent(id, new Date()));
		}).delayElements(Duration.ofSeconds(1));
	}

}
