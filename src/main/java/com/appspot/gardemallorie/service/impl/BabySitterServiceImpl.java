package com.appspot.gardemallorie.service.impl;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.service.BabySitterService;

public class BabySitterServiceImpl implements BabySitterService {

	static private Sort SORT_BY_FIRST_NAME = new Sort("firstName");
	
	@Transactional(propagation = NOT_SUPPORTED)
    public List<BabySitter> findAllBabySitters() {
        return babySitterRepository.findAll(SORT_BY_FIRST_NAME);
    }
    
	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public Page<BabySitter> findAllBabySitters(Pageable pageable) {
        return babySitterRepository.findAll(pageable);
    }
    
}
