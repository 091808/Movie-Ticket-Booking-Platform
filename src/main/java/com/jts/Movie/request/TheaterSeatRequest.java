package com.jts.Movie.request;

import lombok.Data;

@Data
public class TheaterSeatRequest {
	private String address;
	private Integer NoOfSeatInRow;
	private Integer NoOfPremiumSeat;
	private Integer NoOfClassicSeat;
}
