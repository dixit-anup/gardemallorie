package com.appspot.gardemallorie.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import com.appspot.gardemallorie.reference.BabySitterType;
import javax.persistence.Enumerated;
import com.appspot.gardemallorie.reference.LocalisationType;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooSerializable
@RooJson
public class Garde {

    /**
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date le;

    /**
     */
    @Enumerated
    private BabySitterType par;

    /**
     */
    @Enumerated
    private LocalisationType a;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date prevuDebut;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date prevuFin;

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

}
