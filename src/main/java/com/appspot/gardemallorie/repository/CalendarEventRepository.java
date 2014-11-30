package com.appspot.gardemallorie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;

@RooJpaRepository(domainType = CalendarEvent.class)
public interface CalendarEventRepository {
	
	@Query("select calendarEvent from CalendarEvent calendarEvent where calendarEvent.babySitting = ?1")
	List<CalendarEvent> findByBabySitting(BabySitting babySitting);
	
}
