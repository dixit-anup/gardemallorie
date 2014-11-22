package com.appspot.gardemallorie.service.impl;

import static com.appspot.gardemallorie.domain.CalendarEventType.BABY_SITTER;
import static com.appspot.gardemallorie.domain.CalendarEventType.BACK;
import static com.appspot.gardemallorie.domain.CalendarEventType.GO;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;
import com.appspot.gardemallorie.domain.CalendarEventType;
import com.appspot.gardemallorie.domain.Location;
import com.appspot.gardemallorie.security.google.oauth2.Utils;
import com.appspot.gardemallorie.service.CalendarService;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

@Configurable
public class GoogleCalendarService implements CalendarService {

	@Value("${defaultTimeZone}")
	private TimeZone defaultTimeZone;

	@Value("${goBackDurationInMs}")
	private long goBackDurationInMs;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${summaryFormat}")
	private String summaryFormat;
	
	@Value("${summarySuffix.babySitterEvent}")
	private String summarySuffix4BabySitterEvent;
	
	@Value("${summarySuffix.backEvent}")
	private String summarySuffix4BackEvent;
	
	@Value("${summarySuffix.goEvent}")
	private String summarySuffix4GoEvent;

	@Override
	public void deleteEvents(BabySitting babySitting) {
		
		logger.debug("deleteEvents()");
		
		execute(babySitting, new CalendarCallback<Void>() {

			@Override
			public Void doWithCalendar(
					BabySitting babySitting, Calendar calendar, String calendarId, Event event, String eventId, CalendarEventType eventType) throws IOException {

				Void result = calendar.events().delete(calendarId, eventId).setSendNotifications(true).execute();
				
				babySitting.getCalendarEvents().remove(eventType);
				
				return result;
			}});
		
	}
	
	protected <T> void execute(BabySitting babySitting, CalendarCallback<T> callback) {
		
		Map<CalendarEventType, Event> babySittingEvents = new HashMap<CalendarEventType, Event>(3);
		Calendar calendar;
		String calendarId = Utils.getUserEmail();

		babySittingEvents.put(BABY_SITTER, new EventBuilder(babySitting).buildBabySitterEvent());
		babySittingEvents.put(BACK, new EventBuilder(babySitting).buildBackEvent());
		babySittingEvents.put(GO, new EventBuilder(babySitting).buildGoEvent());
		logger.info("babySittingEvents: {}", babySittingEvents);

		logger.info("calendarId: {}", calendarId);
		
		try {
			calendar = Utils.loadCalendarClient();

			for (Map.Entry<CalendarEventType, Event> entry : babySittingEvents.entrySet()) {

				Event event = entry.getValue();
				String eventId = null;
				CalendarEventType eventType = entry.getKey();
				T result;
				
				if (event != null) {
					
					Collection<CalendarEvent> calendarEvents = babySitting.getCalendarEvents();
					logger.info("calendarEvents: {}", calendarEvents);
					if (calendarEvents != null) {
						for (CalendarEvent calendarEvent : calendarEvents) {
							if (calendarEvent.getType().equals(eventType)) {
								eventId = calendarEvent.getExternalId();
							}
						}
					}
					logger.error("eventId: {}", eventId);

					result = callback.doWithCalendar(babySitting, calendar, calendarId, event, eventId, eventType);
					logger.info("result: {}", result);
				}
			}

		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void insertEvents(BabySitting babySitting) {
		
		logger.debug("insertEvents()");
		
		execute(babySitting, new CalendarCallback<Event>() {

			@Override
			public Event doWithCalendar(
					BabySitting babySitting, Calendar calendar, String calendarId, Event event, String eventId, CalendarEventType eventType) throws IOException {
				
				Event resultEvent = calendar.events().insert(calendarId, event).setSendNotifications(true).execute();

				CalendarEvent calendarEvent = new CalendarEvent();
				calendarEvent.setBabySitting(babySitting);
				calendarEvent.setExternalId(resultEvent.getId());
				calendarEvent.setType(eventType);
				calendarEvent.persist();

				return resultEvent;
			}});
		
	}

	@Override
	public void updateEvents(BabySitting babySitting) {

		logger.debug("updateEvents()");
		
		execute(babySitting, new CalendarCallback<Event>() {

			@Override
			public Event doWithCalendar(
					BabySitting babySitting, Calendar calendar, String calendarId, Event event, String eventId, CalendarEventType eventType) throws IOException {
				return calendar.events().update(calendarId, eventId, event).setSendNotifications(true).execute();
			}});
	}
	
	interface CalendarCallback<T> {
		
		T doWithCalendar(BabySitting babySitting, Calendar calendar, String calendarId, Event event, String eventId, CalendarEventType eventType) throws IOException;

	}
	
	class EventBuilder {
		
		private BabySitting babySitting;
		private Event event;

		public EventBuilder(BabySitting babySitting) {
			super();
			this.babySitting = babySitting;
		}
		
		public Event buildBabySitterEvent() {
			Event event = buildEvent(babySitting.getBabySitter(), summarySuffix4BabySitterEvent);
			if (event != null) {
				long plannedBeginning = buildDayTime(babySitting.getPlannedBeginning());
				long plannedEnd = buildDayTime(babySitting.getPlannedEnd());
				event.setStart(buildEventDateTime(plannedBeginning));
				event.setEnd(buildEventDateTime(plannedEnd));
			}
			return event;
		}
		
		public Event buildBackEvent() {
			Event event = buildEvent(babySitting.getBack(), summarySuffix4BackEvent);
			if (event != null) {
				long plannedEnd = buildDayTime(babySitting.getPlannedEnd());
				event.setStart(buildEventDateTime(plannedEnd));
				event.setEnd(buildEventDateTime(plannedEnd + goBackDurationInMs));
			}
			return event;
		}
		
		long buildDayTime(Date time) {
			return babySitting.getDay().getTime() + (time == null ? 0 : time.getTime() - defaultTimeZone.getRawOffset());
		}
		
		Event buildEvent(BabySitter babySitter, String summarySuffix) {
			if (babySitter == null || babySitter.getEmail() == null) {
				return null;
			}
			else {
				event = new Event();
				// Attendees
				EventAttendee eventAttendee = new EventAttendee().setEmail(babySitter.getEmail());
				event.setAttendees(Arrays.asList(eventAttendee));
				// Location
				Location location = babySitting.getLocation();
				if (location != null) {
					event.setLocation(location.getName());
				}
				// Summary
				event.setSummary(String.format(summaryFormat, summarySuffix));
				return event;
			}
		}
		
		EventDateTime buildEventDateTime(long dateTime) {
			return new EventDateTime()
				.setDateTime(new DateTime(dateTime));
		}
		
		public Event buildGoEvent() {
			Event event = buildEvent(babySitting.getGo(), summarySuffix4GoEvent);
			if (event != null) {
				long plannedBeginning = buildDayTime(babySitting.getPlannedBeginning());
				event.setStart(buildEventDateTime(plannedBeginning - goBackDurationInMs));
				event.setEnd(buildEventDateTime(plannedBeginning));
			}
			return event;
		}
		
	}
}
