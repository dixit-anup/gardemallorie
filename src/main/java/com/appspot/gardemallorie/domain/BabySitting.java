package com.appspot.gardemallorie.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static javax.persistence.FetchType.LAZY;
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
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date day;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter babySitter;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date plannedBeginning;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter go;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
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
    @Temporal(TemporalType.TIME)
    private Date declaredEnd;

}
