package com.appspot.gardemallorie.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;
import com.appspot.gardemallorie.repository.CalendarEventRepository;
import com.appspot.gardemallorie.service.CalendarEventService;

@Service
public class CalendarEventServiceImpl implements CalendarEventService {

    @Autowired
    CalendarEventRepository calendarEventRepository;
    
	@Override
	@Transactional
    public void deleteCalendarEvent(CalendarEvent calendarEvent) {
        calendarEventRepository.delete(calendarEvent);
    }
    
	@Override
    public CalendarEvent findCalendarEvent(Long id) {
        return calendarEventRepository.findOne(id);
    }
    
	@Override
	public Collection<CalendarEvent> findCalendarEventsByBabySitting(BabySitting babySitting) {
		return calendarEventRepository.findByBabySitting(babySitting);
	}

	@Override
	@Transactional
    public void saveCalendarEvent(CalendarEvent calendarEvent) {
        calendarEventRepository.save(calendarEvent);
    }

	@Override
	public List<CalendarEvent> findAllCalendarEvents() {
		return calendarEventRepository.findAll();
	}

}
