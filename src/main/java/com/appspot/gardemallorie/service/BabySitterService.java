package com.appspot.gardemallorie.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.BabySitter;

@RooService(domainTypes = { BabySitter.class })
public interface BabySitterService {
	
	void deleteBabySitter(Long id);

	List<BabySitter> findAllBabySitters();
	
	Page<BabySitter> findAllBabySitters(Pageable pageable);

	BabySitter findBabySitter(Long id);

    void saveBabySitter(BabySitter babySitter);    

    BabySitter updateBabySitter(BabySitter babySitter);    

}
