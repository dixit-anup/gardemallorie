package com.appspot.gardemallorie.service;

public class CalendarException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1062576925354951178L;

	public CalendarException() {
		super();
	}

	public CalendarException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CalendarException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CalendarException(String arg0) {
		super(arg0);
	}

	public CalendarException(Throwable arg0) {
		super(arg0);
	}

}
