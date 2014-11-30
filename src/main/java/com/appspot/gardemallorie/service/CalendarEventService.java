package com.appspot.gardemallorie.service;
import java.util.Collection;

import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;

@RooService(domainTypes = { com.appspot.gardemallorie.domain.CalendarEvent.class })
public interface CalendarEventService {

	Collection<CalendarEvent> findCalendarEventsByBabySitting(BabySitting babySitting);

}
