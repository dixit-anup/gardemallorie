package com.appspot.gardemallorie.domain;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@NamedQueries(
	@NamedQuery(
		name = "findCalendarEventsByBabySitting",
		query = "select calendarEvent from CalendarEvent calendarEvent where calendarEvent.babySitting = ?1"
	)
)
@RooJavaBean
@RooToString
@RooJpaEntity
public class CalendarEvent {

    /**
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private BabySitting babySitting;

    /**
     */
    @NotNull
    private String externalId;

    /**
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private CalendarEventType type;
}
