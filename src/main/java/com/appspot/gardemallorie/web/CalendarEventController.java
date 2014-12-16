package com.appspot.gardemallorie.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appspot.gardemallorie.service.CalendarService;

@Controller
@RequestMapping("/calendarevents")
public class CalendarEventController {

	@Autowired
	CalendarService calendarService;
	
    @RequestMapping(value = "/deleteOrphans", produces = "text/html")
    public String deleteOrphans(Model uiModel) {

    	calendarService.deleteOrphanEvents();
        
        return "redirect:/";
    }
    
}
