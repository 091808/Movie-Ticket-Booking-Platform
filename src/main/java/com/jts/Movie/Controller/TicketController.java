package com.jts.Movie.Controller;

import org.spingframework.beans.factory.annotation.Autowired;
import org.spingframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMpping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jts.Movie.request.TheaterRequest;
import com.jts.Movie.request.TicketRequest;
import com.jts.Movie.response.TicketResponse;
import com.jts.Movie.services.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping("/book")
	public ResponseEntity<Object> ticketBooking(@RequestBody TicketRequest ticketRequest) {
		try {
			TicketResponse result = ticketService.ticketBooking(ticketRequest);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
