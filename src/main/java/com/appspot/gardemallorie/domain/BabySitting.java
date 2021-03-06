package com.appspot.gardemallorie.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class BabySitting {

    private static final long MILLISECONDS_PER_HOURS = 1000 * 60 * 60;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date day;

    /**
     * Google Datastore does not support eager fetching.
     */
    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private BabySitter babySitter;

    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date plannedBeginning;

    /**
     * Google Datastore does not support eager fetching.
     */
    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private BabySitter go;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date plannedEnd;

    /**
     * Google Datastore does not support eager fetching.
     */
    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private BabySitter back;

    /**
     */
    @Size(max = 200)
    private String comment;

    /**
     */
    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date declaredEnd;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date chargedEnd;

    /**
     */
    @Transient
    private float extraHours;

    /**
     */
    @Transient
    private float childcareVouchers;

    /**
     */
    @Transient
    private Date copyUntil;
    
    @PostLoad
    void onLoad() {
        if (babySitter.isBilling()) {
            // Childcare vouchers
            childcareVouchers = babySitter.getChildcareVouchers();
            // Extra hours
            if (declaredEnd != null) {
                Date extraHoursBeginning = babySitter.getExtraHoursBeginning();
                extraHours = (float) (declaredEnd.getTime() - extraHoursBeginning.getTime()) / MILLISECONDS_PER_HOURS;
            }
        }
    }

}
