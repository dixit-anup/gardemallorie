package com.appspot.gardemallorie.domain;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@NamedQueries({
	@NamedQuery(
		name = "Location.findAll",
		query = "select location From Location location order by location.name asc"
	)
})
@RooJavaBean
@RooToString
@RooJpaEntity
public class Location {

    /**
     */
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
}
