package com.appspot.gardemallorie.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;
import com.appspot.gardemallorie.service.CalendarService;
import com.appspot.gardemallorie.service.impl.GoogleCalendarService;


privileged aspect BabySittingController_Calendar {
    
	declare precedence: com.appspot.gardemallorie.domain.BabySitting_Roo_Jpa_ActiveRecord;

	private final CalendarService calendarService = new GoogleCalendarService();

	String around(BabySittingController babySittingController, BabySitting babySitting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest):
        args(babySitting, bindingResult, uiModel, httpServletRequest)
        && (
    		execution( public String BabySittingController.create(BabySitting, BindingResult, Model, HttpServletRequest) ) ||
    		execution( public String BabySittingController.update(BabySitting, BindingResult, Model, HttpServletRequest) )
		)
		&& target(babySittingController)
    {
		String result = proceed(babySittingController, babySitting, bindingResult, uiModel, httpServletRequest);

		//BabySitting.entityManager().refresh(babySitting);
		//Collection<CalendarEvent> calendarEvents = babySitting.getCalendarEvents();
		Collection<CalendarEvent> calendarEvents = BabySitting.findBabySitting(babySitting.getId()).getCalendarEvents();
		LoggerFactory.getLogger(BabySittingController.class).debug("calendarEvents: {}", calendarEvents);
		if (calendarEvents == null || calendarEvents.isEmpty()) {
			calendarService.insertEvents(babySitting);
		}
		else {
			calendarService.updateEvents(babySitting);
		}
		
		return result;
	}
	
	String around(BabySittingController babySittingController, Long id, Integer page, Integer size, Model uiModel):
        args(id, page, size, uiModel)
        && execution( public String BabySittingController.delete(Long, Integer, Integer, Model) )
		&& target(babySittingController)
    {
        BabySitting babySitting = BabySitting.findBabySitting(id);
		Collection<CalendarEvent> calendarEvents = babySitting.getCalendarEvents();
		LoggerFactory.getLogger(BabySittingController.class).debug("calendarEvents: {}", calendarEvents);

        String result = proceed(babySittingController, id, page, size, uiModel);

		calendarService.deleteEvents(babySitting);
		
		return result;
	}

}
