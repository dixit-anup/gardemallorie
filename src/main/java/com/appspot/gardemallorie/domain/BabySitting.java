package com.appspot.gardemallorie.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJson
@RooToString
@RooJpaActiveRecord(
	finders = {
		"findBabySittingsByDayBetween", "findBabySittingsByDayGreaterThanEquals", "findBabySittingsByDayLessThanEquals",
		"findBabySittingsByBabySitter",
		"findBabySittingsByGo",
		"findBabySittingsByBack", 
		//"findBabySittingsByPlannedBeginningBetween", "findBabySittingsByPlannedBeginningEquals", "findBabySittingsByPlannedBeginningGreaterThan", "findBabySittingsByPlannedBeginningGreaterThanEquals", "findBabySittingsByPlannedBeginningLessThan", "findBabySittingsByPlannedBeginningLessThanEquals", "findBabySittingsByPlannedBeginningNotEquals"
	}
)
public class BabySitting {
	
    /**
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Temporal(DATE)
    private Date day;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter babySitter;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date plannedBeginning;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter go;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date plannedEnd;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter back;

    /**
     */
    @Size(max = 200)
    private String comment;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private Location location;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date declaredEnd;

    @Transient
    private double extraHours;

    @PostLoad
    void postLoad() {
    	if (babySitter.isBilling() && declaredEnd != null) {
        	Date sixOClock = (Date) declaredEnd.clone();
        	sixOClock.setHours(18);
        	sixOClock.setMinutes(0);
        	sixOClock.setSeconds(0);
        	extraHours = (float)(declaredEnd.getTime() - sixOClock.getTime()) / (1000 * 60 * 60);
    	}
    }

}
