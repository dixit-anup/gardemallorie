package com.appspot.gardemallorie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.BabySitter;

@RooService(domainTypes = { BabySitter.class })
public interface BabySitterService {
	
	void deleteBabySitter(Long id);

	Page<BabySitter> findAllBabySitters(Pageable pageable);

}
