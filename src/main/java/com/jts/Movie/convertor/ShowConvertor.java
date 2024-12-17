package com.jts.Movie.convertor;

import com.jts.Movie.entities.Show;
import com.jts.movie.request.ShowRequest;

public class ShowConvertor {
	
	public static Show showDtoToShow(ShowRequest showRequest) {
		Show show = Show.builder()
				.time(showRequest.getShowStartTime())
				.date(showRequest.getShowDate())
				.build();
		
		return show;
	}
}