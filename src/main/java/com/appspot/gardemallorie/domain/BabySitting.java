package com.appspot.gardemallorie.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIME;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooJpaActiveRecord(finders = { "findBabySittingsByDayBetween", "findBabySittingsByDayGreaterThanEquals", "findBabySittingsByDayLessThanEquals", "findBabySittingsByBabySitter", "findBabySittingsByGo", "findBabySittingsByBack" })
@RooToString
public class BabySitting {

    private static final long MILLISECONDS_PER_HOURS = 1000 * 60 * 60;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Temporal(DATE)
    private Date day;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter babySitter;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date plannedBeginning;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter go;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date plannedEnd;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private BabySitter back;

    @Size(max = 200)
    private String comment;

    @Column(nullable = true)
    @ManyToOne(fetch = LAZY)
    private Location location;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date declaredEnd;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date chargedEnd;

    @Transient
    private float extraHours;

    @Transient
    private float childcareVouchers;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    @Transactional
    public static long countFindBabySittingsByDayAndBabySitter(Date day, Long babySitter) {
        return 0;
    }

    @Transactional
    public static TypedQuery<BabySitting> findBabySittingsByDayAndBabySitter(Date day, Long babySitter) {
        StringBuilder qlString = new StringBuilder("select b from BabySitting as b");
        List<String> where = new ArrayList<String>(2);
        if (day != null) {
            where.add("b.day >= :day");
        }
        if (babySitter != null) {
            where.add("(b.babySitter = :babySitter or b.back = :babySitter or b.go = :babySitter)");
        }
        if (!where.isEmpty()) {
            qlString.append(" where");
            for (int i = 0; i < where.size(); i++) {
                if (i > 0) {
                    qlString.append(" and");
                }
                qlString.append(' ').append(where.get(i));
            }
        }
        //System.out.println("################## " + qlString);
        return entityManager().createQuery(qlString.toString(), BabySitting.class).setParameter("day", day).setParameter("babySitter", babySitter);
    }

}
