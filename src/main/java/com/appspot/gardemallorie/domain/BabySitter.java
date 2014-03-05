package com.appspot.gardemallorie.domain;

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
    private String lastName;

    /**
     */
    private String color;

}
