package com.jts.Movie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.Movie.convertor.TheaterConvertor;
import com.jts.Movie.entities.Theater;
import com.jts.Movie.entities.TheaterSeat;
import com.jts.Movie.enums.SeatType;
import com.jts.Movie.exception.TheaterIsExist;
import com.jts.Movie.exception.TheaterIsNotExist;
import com.jts.Movie.repositories.TheaterRepository;
import com.jts.Movie.request.TheaterRequest;
import com.jts.Movie.request.TheaterSeatRequest;

import java.util.List;

@Service
public class TheaterService {

	@Autowired
	private TheaterRepository theaterRepository;
	
	public String addTheater(TheaterRequest theaterRequest) throws TheaterIsExist{
		if(theaterRepository.findByAddress(theaterRequest.getAddress()) != null) {
			throw new TheaterIsExist();
		}
		
		Theater theater = TheaterConvertor.theaterDtoToTheater(theaterRequest);
		
		theaterRepository.save(theater);
		return "Theater has been saved Successfully";
	}
	
	public String addTheaterSeat(TheaterSeatRequest entryDto) throws TheaterIsNotExist {
		if(theaterRepository.findByAddress(entryDto.getAddress()) == null) {
			throw new TheaterIsNotExist();
		}
	}
}