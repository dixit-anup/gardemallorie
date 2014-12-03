package com.appspot.gardemallorie.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.appspot.gardemallorie.domain.Location;
import com.appspot.gardemallorie.service.LocationService;

public class LocationServiceImpl implements LocationService {

	static private Sort SORT_BY_NAME = new Sort("name");
	
	public List<Location> findAllLocations() {
        return locationRepository.findAll(SORT_BY_NAME);
    }
    
}
