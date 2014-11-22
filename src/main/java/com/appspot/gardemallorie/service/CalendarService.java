package com.appspot.gardemallorie.service;

import com.appspot.gardemallorie.domain.BabySitting;

public interface CalendarService {
	
	void deleteEvents(BabySitting babySitting);

	void insertEvents(BabySitting babySitting);
	
	void updateEvents(BabySitting babySitting);

}
