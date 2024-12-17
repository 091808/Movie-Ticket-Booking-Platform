package com.jts.Movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jts.movie.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	Movie findByMoiveName(String name);
}
