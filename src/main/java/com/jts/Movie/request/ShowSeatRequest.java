package com.jts.Movie.request;

import lombok.Data;

@Data
public class ShowSeatRequest {
	private Integer showId;
	private Integer priceOfPremiumSeat;
	private Integer priceOfClassicSeat;
}
