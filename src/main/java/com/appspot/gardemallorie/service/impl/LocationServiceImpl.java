package com.appspot.gardemallorie.service.impl;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.Location;
import com.appspot.gardemallorie.service.LocationService;

public class LocationServiceImpl implements LocationService {

	static private Sort SORT_BY_NAME = new Sort("name");

	@Override
	public void deleteLocation(Long id) {
		locationRepository.delete(id);
	}
	
	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public List<Location> findAllLocations() {
        return locationRepository.findAll(SORT_BY_NAME);
    }
    
	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public Page<Location> findAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }
    
}
