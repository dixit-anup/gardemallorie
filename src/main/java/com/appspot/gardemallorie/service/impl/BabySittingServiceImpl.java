package com.appspot.gardemallorie.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.BabySittingService;

public class BabySittingServiceImpl implements BabySittingService {

	private static final Sort SORT_BY_DAY = new Sort("day");
	
	@Override
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public long countBabySittingsByDayGreaterThanEquals(Date day) {
		return babySittingRepository.countByDayGreaterThanEqual(day);
	}

	@Override
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public long countNextBabySittings() {
		return babySittingRepository.countByDayGreaterThanEqual(new Date());
	}

	@Override
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BabySitting> findAllBabySittingsOrderByDay() {
		return babySittingRepository.findAll(new PageRequest(0, Integer.MAX_VALUE, SORT_BY_DAY)).getContent();
	}

	@Override
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BabySitting> findNextBabySittings() {
		return findNextBabySittings(0, Integer.MAX_VALUE);
	}

	@Override
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BabySitting> findNextBabySittings(int firstResult, int maxResults) {
		return babySittingRepository.findByDayGreaterThanEqual(new Date(), new PageRequest(firstResult / maxResults, maxResults, SORT_BY_DAY)).getContent();
	}

}
