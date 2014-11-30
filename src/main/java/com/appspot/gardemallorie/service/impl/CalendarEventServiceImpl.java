package com.appspot.gardemallorie.service.impl;
import java.util.Collection;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;
import com.appspot.gardemallorie.service.CalendarEventService;

public class CalendarEventServiceImpl implements CalendarEventService {

	@Override
	public Collection<CalendarEvent> findCalendarEventsByBabySitting(
			BabySitting babySitting) {
		return calendarEventRepository.findByBabySitting(babySitting);
	}

}
