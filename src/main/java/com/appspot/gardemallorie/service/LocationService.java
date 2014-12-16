package com.appspot.gardemallorie.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.Location;

@RooService(domainTypes = { Location.class })
public interface LocationService {

	void deleteLocation(Long id);
	
	List<Location> findAllLocations();    

	Page<Location> findAllLocations(Pageable pageable);

	Location findLocation(Long id);

    void saveLocation(Location location);    

    Location updateLocation(Location location);

}
