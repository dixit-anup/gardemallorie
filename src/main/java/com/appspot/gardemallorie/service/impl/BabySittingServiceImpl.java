package com.appspot.gardemallorie.service.impl;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.BabySittingService;

public class BabySittingServiceImpl implements BabySittingService {

	private static final Sort SORT_BY_DAY = new Sort("day");
	
	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public long countBabySittingsByDayGreaterThanEquals(Date day) {
		return babySittingRepository.countByDayGreaterThanEqual(day);
	}

	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public Page<BabySitting> findAllBabySittings(Pageable pageable) {
		return babySittingRepository.findAll(pageable);
	}
	
	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public List<BabySitting> findAllBabySittingsOrderByDay() {
		return babySittingRepository.findAll(SORT_BY_DAY);
	}

	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public Page<BabySitting> findNextBabySittings(Pageable pageable) {
		return babySittingRepository.findByDayGreaterThanEqual(new Date(), pageable);
	}

}
