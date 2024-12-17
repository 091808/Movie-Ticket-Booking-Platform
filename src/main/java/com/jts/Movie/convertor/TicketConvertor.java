package com.jts.Movie.convertor;

import com.jts.Movie.entities.Show;
import com.jts.Movie.entities.Ticket;
import com.jts.Movie.response.TicketResponse;

public class TicketConvertor {
	
	public static TicketResponse returnTicket(Show show, Ticket ticket) {
		TicketResponse ticketResponseDto = TicketResponse.builder()
				.bookedSeats(ticket.getBookedSeats())
				.address(Show.getTheater().getAddress())
				.theaterName(show.getTheater().getName())
				.movieName(show.getMovie().getMovieName())
				.date(show.getDate())
				.time(show.gettime())
				.totalPrice(ticket.getTotalTicketsPrice())
				.build();
		
		return ticketResponseDto;
	}

}
