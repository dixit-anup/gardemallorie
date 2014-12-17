package com.appspot.gardemallorie.web;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appspot.gardemallorie.service.CalendarService;

@Controller
@RequestMapping(produces = TEXT_HTML_VALUE, value = "/calendarevents")
public class CalendarEventController {

	@Autowired
	CalendarService calendarService;
	
    @RequestMapping(value = "/deleteOrphans")
    public String deleteOrphans(Model uiModel) {

    	calendarService.deleteOrphanEvents();
        
        return "redirect:/";
    }
    
}
