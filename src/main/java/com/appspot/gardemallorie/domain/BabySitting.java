package com.appspot.gardemallorie.domain;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
		"findBabySittingsByDayBetween", "findBabySittingsByDayEquals", "findBabySittingsByDayGreaterThan", "findBabySittingsByDayGreaterThanEquals", "findBabySittingsByDayLessThan", "findBabySittingsByDayLessThanEquals", "findBabySittingsByDayNotEquals",
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
    @DateTimeFormat(pattern = "dd/MM/yyyy, EEE")
    private Date day;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date plannedBeginning;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date plannedEnd;

    /**
     */
    @Size(max = 200)
    private String comment;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date declaredBeginning;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date declaredEnd;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date chargedBeginning;

    /**
     */
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date chargedEnd;

    @Column(nullable = true)
    @ManyToOne
    private BabySitter babySitter;

    @Column(nullable = true)
    @ManyToOne
    private Location location;

    @Column(nullable = true)
    @ManyToOne
    private BabySitter back;

    @Column(nullable = true)
    @ManyToOne
    private BabySitter go;
}
