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
		
		Collection<CalendarEvent> calendarEvents = CalendarEvent.findCalendarEventsByBabySitting(babySitting);

		execute(babySitting, calendarEvents, new CalendarCallback<Void>() {

			@Override
			public Void doWithCalendar(BabySitting babySitting,
					Calendar calendar, String calendarId, Event event,
					CalendarEvent actualEvent,
					CalendarEventType actualEventType) throws IOException {

				Void result = calendar.events().delete(calendarId, actualEvent.getExternalId()).setSendNotifications(true).execute();
				
				// TODO : use transactions and avoid the findCalendarEvent(...) method 
				CalendarEvent.findCalendarEvent(actualEvent.getId()).remove();
				
				return result;
			}

		});
		
	}
	
	protected <T> void execute(BabySitting babySitting, Collection<CalendarEvent> actualEvents, CalendarCallback<T> callback) {
		
		Calendar calendar;
		String calendarId = Utils.getUserEmail();
		Map<CalendarEventType, Event> events = new HashMap<CalendarEventType, Event>(3);

		events.put(BABY_SITTER, new EventBuilder(babySitting).buildBabySitterEvent());
		events.put(BACK, new EventBuilder(babySitting).buildBackEvent());
		events.put(GO, new EventBuilder(babySitting).buildGoEvent());

		logger.debug("actual events: {}", actualEvents);
		logger.debug("calendarId: {}", calendarId);
		logger.debug("events: {}", events);
		
		try {
			calendar = Utils.loadCalendarClient();

			for (Map.Entry<CalendarEventType, Event> entry : events.entrySet()) {

				CalendarEvent actualEvent = null;
				CalendarEventType eventType = entry.getKey();
				Event event = entry.getValue();
				T result;
				
				if (event != null) {
					
					if (actualEvents != null) {
						for (CalendarEvent calendarEvent : actualEvents) {
							if (calendarEvent.getType().equals(eventType)) {
								actualEvent = calendarEvent;
							}
						}
					}
					logger.debug("actualEvent: {}", actualEvent);

					result = callback.doWithCalendar(babySitting, calendar, calendarId, event, actualEvent, eventType);
					logger.debug("result: {}", result);
				}
			}

		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
			//TODO: create a CalendarException
			throw new RuntimeException(e);
		}

	}

	@Override
	public void saveEvents(BabySitting babySitting) {
		
		logger.debug("saveEvents()");
		
		Collection<CalendarEvent> calendarEvents = CalendarEvent.findCalendarEventsByBabySitting(babySitting);
		logger.debug("calendarEvents: {}", calendarEvents);
		
		if (calendarEvents == null || calendarEvents.isEmpty()) {
			
			execute(babySitting, calendarEvents, new CalendarCallback<Event>() {

				@Override
				public Event doWithCalendar(BabySitting babySitting,
						Calendar calendar, String calendarId, Event event,
						CalendarEvent actualEvent,
						CalendarEventType actualEventType) throws IOException {

					logger.debug("inserting events");

					Event resultEvent = calendar.events().insert(calendarId, event).setSendNotifications(true).execute();

					CalendarEvent calendarEvent = new CalendarEvent();
					calendarEvent.setBabySitting(babySitting);
					calendarEvent.setExternalId(resultEvent.getId());
					calendarEvent.setType(actualEventType);
					calendarEvent.persist();

					return resultEvent;
				}
			});
		}
		else {
			
			execute(babySitting, calendarEvents, new CalendarCallback<Event>() {

				@Override
				public Event doWithCalendar(BabySitting babySitting,
						Calendar calendar, String calendarId, Event event,
						CalendarEvent actualEvent,
						CalendarEventType actualEventType) throws IOException {

					logger.debug("updating events");

					return calendar.events().update(calendarId, actualEvent.getExternalId(), event).setSendNotifications(true).execute();
				}
			});
		}
		
		
	}

	interface CalendarCallback<T> {
		
		T doWithCalendar(BabySitting babySitting, Calendar calendar, String calendarId, Event event, CalendarEvent actualEvent, CalendarEventType actualEventType) throws IOException;

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
			if (babySitter == null || babySitter.getEmail() == null || babySitter.getEmail().isEmpty()) {
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
			return new EventDateTime().setDateTime(new DateTime(dateTime));
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
