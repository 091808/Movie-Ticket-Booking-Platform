package com.jts.Movie.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.Movie.convertor.TicketConvertor;
import com.jts.Movie.entities.Show;
import com.jts.Movie.entities.ShowSeat;
import com.jts.Movie.entities.Ticket;
import com.jts.Movie.entities.User;
import com.jts.Movie.exception.SeatsNotAvailable;
import com.jts.Movie.exception.ShowDoesNotExists;
import com.jts.Movie.exception.UserDoesNotExists;
import com.jts.Movie.repositories.ShowRepository;
import com.jts.Movie.repositories.TicketRepository;
import com.jts.Movie.repositories.UserRepository;
import com.jts.Movie.request.TicketRequest;
import com.jts.Movie.response.TicketResponse;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private ShowRepository showRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public TicketResponse ticketBooking(TicketRequest ticketRequest) throws SeatsNotAvailable, UserDoesNotExists, ShowDoesExists {
		Optional<Show> showOpt = showRepository.findById(ticketRequest.getShowId());
		
		if(showOpt.isEmpty()) {
			throw new ShowDoesNotExists();
		}
		
		Optional<User> userOpt = userRepository.findById(ticketRequest.getUserId());
		
		if(userOpt.isEmpty()) {
			throw new ShowDoesNotExists();
		}
		
		Optional<User> userOpt = userRepository.findById(ticketRequest.getUserId());
		
		if(userOpt.isEmpty()) {
			throw new UserDoesNotExists();
		}
		
		User user = userOpt.get();
		Show show = showOpt.get();
		
		Boolean isSeatAvailable = isSeatAvailable(show.getShowSeatList(), ticketRequest.getRequestSeats());
		
		if(!isSeatAvailable) {
			throw new SeatsNotAvailable();
		}
		
		//count price
		Integer getPriceAndAssignSeats = getPriceAssignSeats(show.getShowSeatList(), ticketRequest.getRequestSeats());
		
		String seats = listToString(ticketRequest.getRequestSeats());
		
		Ticket ticket = new Ticket();
		ticket.setTotalTicketsPrice(getPriceAndAssignSeats);
		ticket.setBookedSeats(seats);
		ticket.setUser(user);
		ticket.setShow(show);
		
		ticket = ticketRepository.save(ticket);
		
		user.getTicketList().add(ticket);
		show.getTicketList().add(ticket);
		userRepository.save(user);
		showRepository.save(show);
		
		return TicketConvertor.returnTicket(show, ticket);
	}
	
	private Boolean isSeatAvailable(List<ShowSeat> showSeatList, List<String> requestSeats) {
		for(ShowSeat showSeat : showSeatList) {
			String seatNo = showSeat.getSeatNo();
			
			if(requestSeats.contains(seatNo) && !showSeat.getIsAvailable()) {
				return false;
			}
		}
		
		return true;
	}
	
	private Integer getPriceAndAssignSeats(List<ShowSeat> showSeatList, List<String> requestSeats) {
		Integer totalAmount = 0;
		
		for(ShowSeat showSeat : showSeatList) {
			if(requestSeats.contains(showSeat.getSeatNo())) {
				totalAmount += showSeat.getPrice();
				showSeat.setIsAvailable(Boolean.FALSE);
			}
		}
		return totalAmount;
	}
	
	private String listToString(List<String> requestSeats) {
		StringBuilder sb = new StringBuilder();
		
		for (String s : requestSeats) {
			sb.append(s).append(",");
		}
		return sb.toString();
	}

}
