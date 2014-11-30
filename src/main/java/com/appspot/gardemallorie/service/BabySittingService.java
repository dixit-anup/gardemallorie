package com.appspot.gardemallorie.service;

import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.appspot.gardemallorie.domain.BabySitting;

@RooService(domainTypes = { com.appspot.gardemallorie.domain.BabySitting.class })
public interface BabySittingService {

	long countBabySittingsByDayGreaterThanEquals(Date day);

	long countNextBabySittings();

	List<BabySitting> findAllBabySittingsOrderByDay();

	List<BabySitting> findNextBabySittings();

	List<BabySitting> findNextBabySittings(int firstResult, int sizeNo);

}
