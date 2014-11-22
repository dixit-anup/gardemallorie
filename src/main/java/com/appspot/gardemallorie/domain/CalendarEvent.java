package com.appspot.gardemallorie.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord
@RooToString
public class CalendarEvent {
	
	private String externalId;

	@Enumerated(EnumType.STRING)
	private CalendarEventType type;
	
	@ManyToOne()
	private BabySitting babySitting;

}
