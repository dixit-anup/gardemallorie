package com.appspot.gardemallorie.domain;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooEquals
@RooJson
public class BabySitter {

    /**
     */
    private String firstName;

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    /**
     */
    private boolean notification;

    /**
     */
    private String email;

    /**
     */
    private String color;

    /**
     */
    private boolean billing;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date extraHoursBeginning;
}
