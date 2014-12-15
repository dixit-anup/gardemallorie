package com.appspot.gardemallorie.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.BabySitting;

@RooService(domainTypes = { BabySitting.class })
public interface BabySittingService {

	void copyBabySittingUntil(Date date, Long id);
	
	void deleteBabySitting(Long id);

	Page<BabySitting> findAllBabySittings(Pageable pageable);

	List<BabySitting> findExtraCharges();
	
	Page<BabySitting> findNextBabySittings(Pageable pageable);

}
