package com.appspot.gardemallorie.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import com.appspot.gardemallorie.reference.BabySitterType;
import com.appspot.gardemallorie.reference.LocalisationType;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionType = Long.class)
@RooJson
public class Garde {

    /**
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd, EEE")
    private Date le;

    /**
     */
    @Enumerated(EnumType.STRING)
    private BabySitterType par;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date prevuDebut;
    
    /**
     */
    @Enumerated(EnumType.STRING)
    private BabySitterType aller;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date prevuFin;

    /**
     */
    @Enumerated(EnumType.STRING)
    private BabySitterType retour;

    /**
     */
    @Enumerated(EnumType.STRING)
    private LocalisationType a;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date reelDebut;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date reelFin;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date factureDebut;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date factureFin;
    
    /**
     */
    @Size(max = 200)
    private String commentaire;

}
