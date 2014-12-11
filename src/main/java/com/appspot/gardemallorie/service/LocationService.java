package com.appspot.gardemallorie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.Location;

@RooService(domainTypes = { Location.class })
public interface LocationService {

	Page<Location> findAllLocations(Pageable pageable);

}
