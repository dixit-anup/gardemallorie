// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect CalendarEvent_Roo_Jpa_Entity {
    
    declare @type: CalendarEvent: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long CalendarEvent.id;
    
    @Version
    @Column(name = "version")
    private Integer CalendarEvent.version;
    
    public Long CalendarEvent.getId() {
        return this.id;
    }
    
    public void CalendarEvent.setId(Long id) {
        this.id = id;
    }
    
    public Integer CalendarEvent.getVersion() {
        return this.version;
    }
    
    public void CalendarEvent.setVersion(Integer version) {
        this.version = version;
    }
    
}
