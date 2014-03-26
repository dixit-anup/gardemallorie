package com.appspot.gardemallorie.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    /**
     */
    private String color;

}
