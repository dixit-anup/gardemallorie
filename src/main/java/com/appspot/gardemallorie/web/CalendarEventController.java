package com.appspot.gardemallorie.web;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appspot.gardemallorie.domain.CalendarEvent;

@RequestMapping("/calendarevents")
@Controller
@RooWebScaffold(path = "calendarevents", formBackingObject = CalendarEvent.class)
public class CalendarEventController {
}
