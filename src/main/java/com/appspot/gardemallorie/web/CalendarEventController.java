package com.appspot.gardemallorie.web;
import com.appspot.gardemallorie.domain.CalendarEvent;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/calendarevents")
@Controller
@RooWebScaffold(path = "calendarevents", formBackingObject = CalendarEvent.class)
public class CalendarEventController {
}
