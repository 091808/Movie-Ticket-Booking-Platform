package com.jts.Movie.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotaion.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jts.movie.request.MovieRequest;
import com.jts.movie.service.MovieService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	private final MovieService movieService;
	public MovieController(MovieService movieService) {
		this.movieService = MovieService;
	}
	
	@GetMapping
	public List<Movie> getMovies(
			@RequestParam(required = false) String title,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormate.ISO.DATE) LocalDate date,
			@RequestParam(required = false) String location,
			@RequestParam(required = false) String genre
			) 
	{return movieService.getAllMovies(title, date, location, genre);}
	
	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable("id") Integer id) {
		return movieService.getMovieById(id); 
	}
	
	@GetMapping("/booking/history")
	public List<BookingHistory> getBookingHistory(){
		return movieService.getBookingHistory();
	}
	
	@PostMapping
	public void addMovie(@RequestBody CinemaProjectApplication.NewMovieRequest movie) {
		movieService.addMovie(movie);
	}
	
	@PostMapping("/booking/{movieId}/{quality}/{totalPrice}")
	public void createBooking(
			@PathVariable("movieId") Integer id,
			@PathVariable("quantity") Integer quantity,
			@PathVariable("totalPrice") Integer totalPrice)
	{
		movieService.bookTickets(id, quantity, totalPrice);
	}
	
	@PostMapping(:/addNew")
	public ResponseEntity<String> addMovie(@RequestBody MovieRequest movieRequest) {
		try {
			String result = movieService.addMovie(movieRequest);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		@DeleteMapping("/{movieId}")
		public void deleteMovie(@PathVariable("movieId") Integer id) {
			movieService.deleteMovie(id);
		}
		
		@PutMapping("/{movieId}")
		public void updateMovie(@PathVariable("movieId") Integer id, @RequestBody CinemaProjectPlatform.NewMovieRequest movie) {
			movieService.updateMovie(id, movie);
		}
	}
}