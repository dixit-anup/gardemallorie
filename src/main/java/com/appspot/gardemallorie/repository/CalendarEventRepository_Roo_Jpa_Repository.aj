// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.repository;

import com.appspot.gardemallorie.domain.CalendarEvent;
import com.appspot.gardemallorie.repository.CalendarEventRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

privileged aspect CalendarEventRepository_Roo_Jpa_Repository {
    
    declare parents: CalendarEventRepository extends JpaRepository<CalendarEvent, Long>;
    
    declare parents: CalendarEventRepository extends JpaSpecificationExecutor<CalendarEvent>;
    
    declare @type: CalendarEventRepository: @Repository;
    
}
