package com.appspot.gardemallorie.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class BabySitter {

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    /**
     */
    private String firstName;

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

    /**
     */
    private float childcareVouchers;
}
