package com.jts.Movie.exception;

public class TheaterDoesNotExists extends Exception {
	private static final long serialVersionUID = 2885350098352987873L;

	public TheaterDoesNotExists() {
		super("Theater does not Exists");
	}
}