package com.rohith.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rohith.domain.Movies;
import com.rohith.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class InitMovies implements CommandLineRunner {

	private final MovieRepository movieRepository;

	@Override
	public void run(String... args) throws Exception {
		movieRepository.deleteAll()
				.thenMany(Flux.just("Lord of the Rings", "Interstellar", "Star Wars", "MI", "Die Hard", "Inception")
						.map(title -> new Movies(title)).flatMap(movieRepository::save))
				.subscribe(null, null, () -> {
					movieRepository.findAll().subscribe(System.out::println);
				});
	}

}
