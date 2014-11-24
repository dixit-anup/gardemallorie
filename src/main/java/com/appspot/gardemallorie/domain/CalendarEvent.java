package com.appspot.gardemallorie.domain;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@NamedQueries(
	@NamedQuery(
		name = "findCalendarEventsByBabySitting",
		query = "select calendarEvent from CalendarEvent calendarEvent where calendarEvent.babySitting = :babySitting"
	)
)
@RooJavaBean
@RooJpaActiveRecord
@RooToString
public class CalendarEvent {
	
	private String externalId;

	@Enumerated(EnumType.STRING)
	private CalendarEventType type;
	
	@ManyToOne(fetch = LAZY)
	private BabySitting babySitting;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public static List<CalendarEvent> findCalendarEventsByBabySitting(BabySitting babySitting) {
		return entityManager()
			.createNamedQuery("findCalendarEventsByBabySitting")
			.setParameter("babySitting", babySitting)
			.getResultList();
	}

}
