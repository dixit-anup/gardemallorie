package com.appspot.gardemallorie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.Location;
import com.appspot.gardemallorie.repository.LocationRepository;
import com.appspot.gardemallorie.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	static private Sort SORT_BY_NAME = new Sort("name");

    @Autowired
    LocationRepository locationRepository;

    @Override
	@Transactional
	public void deleteLocation(Long id) {
		locationRepository.delete(id);
	}
	
	@Override
	public List<Location> findAllLocations() {
        return locationRepository.findAll(SORT_BY_NAME);
    }
    
	@Override
	public Page<Location> findAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }
    
	@Override
	public Location findLocation(Long id) {
		return locationRepository.findOne(id);
	}
    
	@Override
	@Transactional
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }
    
	@Override
	@Transactional
    public Location updateLocation(Location location) {
        return locationRepository.save(location);
    }

}
