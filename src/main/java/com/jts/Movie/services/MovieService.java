package com.jts.Movie.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.jts.Movie.cinema_project.CinemaProjectApplication;
import com.jts.movie.convertor.MovieConvertor;
import com.jts.movie.entities.Movie;
import com.jts.movie.exceptions.MovieAlreadyExist;
import com.jts.movie.repositories.MovieRepository;
import com.jts.movie.request.MovieRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
	
	private final MovieRepository movieRepository;
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	public List<Movie> filterMovies(String title, LocalDate date, String location, String genre) {
		List<Movie> movies = movieRepository.findAll();
		List<Movie> filteredMovies = new ArrayList<>();
	
	for (Movie movie : movies) {
		boolean match = true;
		
		if (title != null && !movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
			match = false;
		}
		if(date != null && !movie.getDate().isEqual(date)) {
			match = false;
		}
		if(location !=null && !movie.getLocation().toLowerCase().contains(location.toLowerCase())) {
			match = false;
		}
		if(genre !=null && !movie.getGenre().toLowerCase().conatins(genre.toLowerCase())) {
			match = false;
		}
		
		if (match) {
			filteredMovies.add(movie);
	}
}

	return filteredMovies;
}
	
	@Autowired
	private MovieRepository movieRepository;
	
	public String addMovie(MovieRequest movieRequest) {
		Movie movieByName = movieRepository.findByMovieName(movieRequest.getMovieName());
		
		if(movieByName != null && movieByName.getLanguage().equals(movieRequest.getLanguage())) {
			throw new MovieAlreadyExist();
		}
		
		Movie movie = MovieConvertor.movieDtoToMovie(movieRequest);
		
		movieRepository.save(movie);
		return "The movie has been added successfully";
	}
}

@GetMapping("/booking/history")
public List<BookingHistory> getBookingHistory() {
	List<Movie> movies = movieRepository.findAll();
	List<BokingHistory> bookingHistory = new ArrayList<>();
	
	for (Movie movie : movies) {
		int bookedTickets = movie.getTotalSeats() - movie.getAvailableSeats();
		
		if(bookedTickets > 0) {
			int totalPrice = bookedTickets * movie.getPrice();
			
			BookingHistory booking = new BookingHistory();
			booking.setId(movie.getId());
			booking.setTitle(movie.getTitle());
			booking.setDirector(movie.getDirector());
			booking.setDescription(movie.getDescription());
			booking.setGenre(movie.getGenre());
			booking.setDate(movie.getDate());
			booking.setLocation(movie.getLocation());
			booking.setBookedTickets(bookedTickets);
			booking.setTotalPrice(totalPrice);
			bookingHistory.add(booking);
		}
	}
	return bookingHistory;
}

@PostMapping
public void addMovie(@RequestBody CinemaProjectApplication.NewMovieRequest request) {
	Movie movie = new Movie();
	movie.setDescription(request.description());
	movie.setDirector(request.director());
	movie.setGenre(request.genre());
	movie.setTitle(request.title());
	movie.setDate(request.date());
	movie.setLocation(request.location());
	movie.setTotalSeats(request.totalSeats());
	movie.setAvailableSeats(request.availableSeats());
	movie.setPrice(request.price());
	movieRepository.save(movie);
}

@DeleteMapping("{movieId}")
public void deleteMovie(@PathVariable("movieId") Integer id) {
	Movie movie = movieRepository.findById(id)
			.orElseThrow(()-> new IllegalArgumentException("Invalid movie ID:" + id));
	
	movieRepository.delete(movie);
}

@PutMapping("{movieId}")
public void updateMovie(@PathVariable("movieId") Integer id,
		@RequestBody CinemaProjectApplication.NewMovieRequest request) {
	
	Movie movie = movieRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid movie ID:" + id));
	
	 movie.setDescription(request.description());
     movie.setDirector(request.director());
     movie.setGenre(request.genre());
     movie.setTitle(request.title());
     movie.setDate(request.date());
     movie.setLocation(request.location());
     movie.setTotalSeats(request.totalSeats());
     movie.setAvailableSeats(request.availableSeats());
     movie.setPrice(request.price());
     movieRepository.save(movie);
 }
}