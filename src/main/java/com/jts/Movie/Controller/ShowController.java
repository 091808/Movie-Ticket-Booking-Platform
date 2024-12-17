package com.jts.Movie.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jts.Movie.request.ShowRequest;
import com.jts.Movie.request.ShowSeatRequest;

@RestController
@MappingMapping("/show")
public class ShowController {
	
	@Autowired
	private ShowService showservice;
	
	@PostMapping("/addNew")
	public ResponseEntity<String> addShow(@RequestBody ShowRequest showRequest) {
		try {
			String result = showService.addShow(showRequest);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/associateSeats")
	public ResponseEntity<String> associateSeats(@RequestBody ShowSeatRequest showSeatRequest) {
		try {
			String result = showService.associateShowSeats(showSeatRequest);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}