package com.appspot.gardemallorie.domain;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = String.class, versionType = Long.class)
@RooSerializable
@RooJson
public class BabySitter {

    /**
     */
    @NotNull
    private String nom;
}
