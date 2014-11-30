package com.appspot.gardemallorie.service;

import java.io.IOException;

import com.google.api.services.calendar.Calendar;

public interface GoogleCalendarService {
	
	Calendar createCalendarClient() throws IOException;

}
