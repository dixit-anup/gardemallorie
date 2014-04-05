package com.appspot.gardemallorie.domain;

import static javax.persistence.TemporalType.TIME;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord
@RooJson
@RooToString
public class BabySitter {

    /**
     */
    private String firstName;

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    private boolean notification;
    
	@Email
	private String email;
	
    /**
     */
    private String color;
    
    private boolean billing;
    
    @Column(nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TIME)
    private Date extraHoursBeginning;

}
