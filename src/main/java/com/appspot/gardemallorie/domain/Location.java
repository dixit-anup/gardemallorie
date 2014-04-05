package com.appspot.gardemallorie.domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooJpaActiveRecord
@RooJson
@RooToString
public class Location {

    /**
     */
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Transactional()
    public static List<Location> findAllLocations() {
        return findAllLocations("name", "asc");
    }
    
}
