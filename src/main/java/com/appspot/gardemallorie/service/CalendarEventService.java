package com.appspot.gardemallorie.service;

import java.util.Collection;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;

@RooService(domainTypes = { com.appspot.gardemallorie.domain.CalendarEvent.class })
public interface CalendarEventService {

	void deleteCalendarEvent(CalendarEvent actualEvent);

	CalendarEvent findCalendarEvent(Long id);
	
	List<CalendarEvent> findAllCalendarEvents();

	Collection<CalendarEvent> findCalendarEventsByBabySitting(BabySitting babySitting);

	void saveCalendarEvent(CalendarEvent calendarEvent);

}
