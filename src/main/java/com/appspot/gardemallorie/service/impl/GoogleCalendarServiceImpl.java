package com.appspot.gardemallorie.service.impl;

import static com.appspot.gardemallorie.domain.CalendarEventType.BABY_SITTER;
import static com.appspot.gardemallorie.domain.CalendarEventType.BACK;
import static com.appspot.gardemallorie.domain.CalendarEventType.GO;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.CalendarEvent;
import com.appspot.gardemallorie.domain.CalendarEventType;
import com.appspot.gardemallorie.domain.Location;
import com.appspot.gardemallorie.service.CalendarEventService;
import com.appspot.gardemallorie.service.CalendarException;
import com.appspot.gardemallorie.service.CalendarService;
import com.appspot.gardemallorie.service.GoogleCalendarService;
import com.appspot.gardemallorie.service.GoogleOauth2Service;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

@Service
@Transactional
public class GoogleCalendarServiceImpl implements CalendarService, GoogleCalendarService {

	@Value("${calendar.applicationName}")
	private String applicationName;
	
	@Autowired
	private CalendarEventService calendarEventService;
	
	@Value("${calendar.defaultTimeZone}")
	private TimeZone defaultTimeZone;

	@Value("${calendar.goBackDurationInMs}")
	private long goBackDurationInMs;

	@Autowired(required = true)
	private GoogleOauth2Service googleOauth2Service;
	
	private final HttpTransport httpTransport = UrlFetchTransport.getDefaultInstance();
	
	private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${calendar.summaryFormat}")
	private String summaryFormat;
	
	@Value("${calendar.summarySuffix.babySitterEvent}")
	private String summarySuffix4BabySitterEvent;
	
	@Value("${calendar.summarySuffix.backEvent}")
	private String summarySuffix4BackEvent;
	
	@Value("${calendar.summarySuffix.goEvent}")
	private String summarySuffix4GoEvent;

	@Override
	public Calendar createCalendarClient() throws IOException {
		
		Credential credential = googleOauth2Service.getCurrentUserCredential();
		return new Calendar.Builder(httpTransport, jsonFactory, credential).setApplicationName(applicationName).build();
	}

	@Override
	public void deleteEvents(BabySitting babySitting) {
		
		logger.debug("deleteEvents()");
		
		execute(babySitting, new CalendarCallback<Void>() {

			@Override
			public Void doWithCalendar(BabySitting babySitting,
					CalendarEvent actualEvent,
					CalendarEventType actualEventType,
					Calendar calendar,
					String calendarId, Event event) throws IOException {

				Void result = calendar.events().delete(calendarId, actualEvent.getExternalId()).setSendNotifications(true).execute();
				
				return result;
			}

		});
		
	}
	
	@Override
	public void deleteOrphanEvents() {
		
		Calendar calendar;
		String calendarId = googleOauth2Service.getCurrentUserEmail();
		List<CalendarEvent> calendarEvents = calendarEventService.findAllCalendarEvents();
		
		try {
			calendar = createCalendarClient();
		}
		catch (IOException e) {
			throw new CalendarException(e);
		}
		
		for (CalendarEvent calendarEvent : calendarEvents) {
			
			BabySitting babySitting = null;
			try {
				babySitting = calendarEvent.getBabySitting();
			}
			catch (Throwable t) {
				logger.warn("Unable to fetch calendarEvent #{} babySitting:", calendarEvent.getId(), t);
			}
			if (babySitting == null) {

				calendarEventService.deleteCalendarEvent(calendarEvent);
				try {
					calendar.events().delete(calendarId, calendarEvent.getExternalId()).setSendNotifications(true).execute();
				} catch (IOException e) {
					logger.warn("Unable to remove event #{}:", calendarEvent.getExternalId(), e);
				}			}
		}
		
	}

	protected <T> void execute(BabySitting babySitting, CalendarCallback<T> callback) {
		
		Collection<CalendarEvent> actualEvents = calendarEventService.findCalendarEventsByBabySitting(babySitting);
		Calendar calendar;
		String calendarId = googleOauth2Service.getCurrentUserEmail();
		Map<CalendarEventType, Event> events = new HashMap<CalendarEventType, Event>(3);

		events.put(BABY_SITTER, new EventBuilder(babySitting).buildBabySitterEvent());
		events.put(BACK, new EventBuilder(babySitting).buildBackEvent());
		events.put(GO, new EventBuilder(babySitting).buildGoEvent());

		logger.debug("actual events: {}", actualEvents);
		logger.debug("calendarId: {}", calendarId);
		logger.debug("events: {}", events);
		
		try {
			
			calendar = createCalendarClient();
	
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
	
					result = callback.doWithCalendar(babySitting, actualEvent, eventType, calendar, calendarId, event);
					logger.debug("result: {}", result);
				}
			}
		}
		catch (IOException e) {
			throw new CalendarException(e);
		}
	}

	@Override
	public void saveEvents(BabySitting babySitting) {
		
		logger.debug("saveEvents()");
		
		execute(babySitting, new CalendarCallback<Event>() {

			@Override
			public Event doWithCalendar(BabySitting babySitting,
					CalendarEvent actualEvent,
					CalendarEventType actualEventType,
					Calendar calendar,
					String calendarId, Event event) throws IOException {

				Event resultEvent;

				// Create event
				if (actualEvent == null) {
					
					logger.debug("inserting event {}", actualEventType);

					resultEvent = calendar.events().insert(calendarId, event).setSendNotifications(true).execute();

					CalendarEvent calendarEvent = new CalendarEvent();
					calendarEvent.setBabySitting(babySitting);
					calendarEvent.setExternalId(resultEvent.getId());
					calendarEvent.setType(actualEventType);
					calendarEventService.saveCalendarEvent(calendarEvent);					
				}
				// Update event
				else {
					
					logger.debug("updating event {}", actualEventType);

					resultEvent = calendar.events().update(calendarId, actualEvent.getExternalId(), event).setSendNotifications(true).execute();
				}
			
				return resultEvent;
			}
		});
		
	}

	interface CalendarCallback<T> {
		
		T doWithCalendar(BabySitting babySitting, CalendarEvent actualEvent, CalendarEventType actualEventType, Calendar calendar, String calendarId, Event event) throws IOException;

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
