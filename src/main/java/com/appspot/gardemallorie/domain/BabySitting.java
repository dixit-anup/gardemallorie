package com.appspot.gardemallorie.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJson
@RooToString
@RooJpaActiveRecord(finders = { "findBabySittingsByDayBetween", "findBabySittingsByDayGreaterThanEquals", "findBabySittingsByDayLessThanEquals", "findBabySittingsByBabySitter", "findBabySittingsByGo", "findBabySittingsByBack" })
public class BabySitting {

    private static final long MILLISECONDS_PER_HOURS = 1000 * 60 * 60;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date day;

    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private BabySitter babySitter;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date plannedBeginning;

    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private BabySitter go;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date plannedEnd;

    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private BabySitter back;

    @Size(max = 200)
    private String comment;

    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date declaredEnd;

    @Transient
    private float extraHours;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date chargedEnd;

    @PostLoad
    void onLoad() {
        if (babySitter.isBilling() && declaredEnd != null) {
            Date extraHoursBeginning = babySitter.getExtraHoursBeginning();
            extraHours = (float) (declaredEnd.getTime() - extraHoursBeginning.getTime()) / MILLISECONDS_PER_HOURS;
        }
    }

    @Autowired
    private transient MailSender mailSender;

    @Autowired
    @Qualifier("newBabySittingMailMessage")
    private transient SimpleMailMessage newBabySittingMailMessage;

    @Autowired
    @Qualifier("updateBabySittingMailMessage")
    private transient SimpleMailMessage updateBabySittingMailMessage;

    @PostPersist
    void onPersist() {
    	sendMailMessage(newBabySittingMailMessage);
    }
    
    @PostUpdate
    void onUpdate() {
    	throw new UnsupportedOperationException();
    }

    void sendMailMessage(SimpleMailMessage templateMessage) {
    	
    	Set<BabySitter> babySitters = new HashSet<BabySitter>(3);
    	babySitters.add(babySitter);
    	babySitters.add(back);
    	babySitters.add(go);
    	List<String> to = new ArrayList<String>();

    	for (BabySitter b : babySitters) {
    		if (b != null && b.isNotification() && b.getEmail() != null) {
    			to.add(b.getEmail());
    		}
    	}

    	if (to.size() > 0) {
	        SimpleMailMessage mailMessage = new SimpleMailMessage(templateMessage);
	        mailMessage.setTo(to.toArray(new String[to.size()]));
	        mailMessage.setText("http://2.gardemallorie.appspot.com/babysittings/" + getId());
	        mailSender.send(mailMessage);
    	}

    }

}
