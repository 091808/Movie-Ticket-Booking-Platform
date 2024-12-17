package com.jts.Movie.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.Movie.convertor.ShowConvertor;
import com.jts.Movie.entities.Movie;
import com.jts.Movie.entities.Show;
import com.jts.Movie.entities.ShowSeat;
import com.jts.Movie.entities.Theater;
import com.jts.Movie.entities.TheaterSeat;
import com.jts.Movie.exception.MovieDoesNotExists;
import com.jts.Movie.exception.ShowDoesNotExists;
import com.jts.Movie.exception.TheaterDoesNotExists;
import com.jts.Movie.repositories.MovieRepository;
import com.jts.Movie.repositories.ShowRepository;
import com.jts.Movie.repositories.TheaterRepository;
import com.jts.Movie.request.ShowRequest;
import com.jts.Movie.request.ShowSeatRequest;

@Service
public class ShowService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private ShowRepository showRepository;

	public String addShow(ShowRequest showRequest) {
		Show show = ShowConvertor.showDtoToShow(showRequest);
		
		Optional<Movie> movieOpt = movieRepository.findById(showRequest.getMovieId());
		
		if(movieOpt.isEmpty()) {
			throw new MovieDoesNotExists();
		}
		
		Optional<Theater> theaterOpt = theaterRepository.findById(showRequest.getTheaterId());
		
		if(theaterOpt.isEmpty()) {
			throw new TheaterDoesNotExists();
		}
		
		Theater theater = theaterOpt.get();
		Movie movie = movieOpt.get();
		
		show.setMovie(movie);
		show.setTheater(theater);
		show = showRepository.save(show);
		
		movie.getShows().add(show);
		theater.getShowList().add(show);
		
		movieRepository.save(movie);
		theaterRepository.save(theater);
		
		return "Show has been added successfully!";
	}
	
	public String associateShowSeats(ShowSeatRequest showSeatRequest) throws ShowDoesNotExists {
		Optional<Show> showOpt = showRepository.findById(showSeatRequest.getShowId());
		
		if(showOpt.isEmpty()) {
			throw new ShowDoesNotExists();
		}
		
		Show show = showOpt.get();
		Theater theater = show.getTheater();
		
		List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();
		
		List<ShowSeat> showSeatList = show.getShowSeatList();
		
		for (TheaterSeat theaterSeat : theaterSeatList) {
			ShowSeat showSeat = new ShowSeat();
			ShowSeat.setSeatNo(theaterSeat.getSeatNo());
			showSeat.setSeatNo(theaterSeat.getSeatType());
			
			if(showSeat.getSeatType().equals(SeatType.CLASSIC)) {
				ShowSeat.setPrice((showSeatRequest.getPriceOfClassicSeat()));
			} else {
				showSeat.setPrice(showSeatRequest.getPriceOfPremiumSeat());
			}
			
			showSeat.setShow(show);
			showSeat.setIsAvailable(Boolean.TRUE);
			showSeat.setIsFoodContains(Boolean.FALSE);
			
			showSeatList.add(showSeat);
		}
		
	showRepository.save(show);
	
    return "Show seats have been associated successfully";)
	}
}
