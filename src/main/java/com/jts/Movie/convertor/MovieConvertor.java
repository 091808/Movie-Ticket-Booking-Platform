package com.jts.Movie.convertor;

import com.jts.Movie.entities.Movie;
import com.jts.Movie.request.MovieRequest;

public class MovieConvertor {
	
	public static Movie movieDtoToMovie(MovieRequest movieRequest) {
		Movie movie = Movie.builder()
				.movieName(movieRequest.getMovieName())
				.duration(movieRequest.getDuration())
				.genre(movieRequest.getGenre())
				.language(movieRequest.getLanguage())
				.releaseDate(movieRequest.getReleaseDate())
				.rating(movieRequest.getRating())
				.build();
		
		return movie;
	}
}