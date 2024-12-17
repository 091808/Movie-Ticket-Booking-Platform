package com.jts.Movie.request;

import java.sql.Date;
import java.sql.Time;

import lombok.Data;

@Data
public class ShowRequest {
	
	private Time showStartTime;
	private Date showData;
	private Integer theaterId;
	private Integer movieId;
}
