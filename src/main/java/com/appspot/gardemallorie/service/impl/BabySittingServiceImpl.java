package com.appspot.gardemallorie.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.BabySittingService;

public class BabySittingServiceImpl implements BabySittingService {

	private static final Sort SORT_BY_DAY = new Sort("day");
	
	@Override
	public long countBabySittingsByDayGreaterThanEquals(Date day) {
		return babySittingRepository.countByDayGreaterThanEqual(day);
	}

	@Override
	public long countNextBabySittings() {
		return babySittingRepository.countByDayGreaterThanEqual(new Date());
	}

	@Override
	public List<BabySitting> findAllBabySittings(Pageable pageable) {
		return babySittingRepository.findAll(pageable).getContent();
	}
	
	@Override
	public List<BabySitting> findAllBabySittingsOrderByDay() {
		return findAllBabySittings(new PageRequest(0, Integer.MAX_VALUE, SORT_BY_DAY));
	}

	@Override
	public List<BabySitting> findNextBabySittings(Pageable pageable) {
		
		Page<BabySitting> page = babySittingRepository.findByDayGreaterThanEqual(new Date(), pageable);
		
		ToStringBuilder message = new ToStringBuilder(page)
			.append("number", page.getNumber())
			.append("numberOfElements", page.getNumberOfElements())
			.append("size", page.getSize())
			.append("totalElements", page.getTotalElements())
			.append("totalPages", page.getTotalPages());
		LoggerFactory.getLogger(getClass()).debug(message.build());
		
		return page.getContent();
	}

}
