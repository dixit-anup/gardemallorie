package com.appspot.gardemallorie.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.service.BabySitterService;

public class BabySitterServiceImpl implements BabySitterService {

	static private Sort SORT_BY_FIRST_NAME = new Sort("firstName");
	
    public List<BabySitter> findAllBabySitters() {
        return babySitterRepository.findAll(SORT_BY_FIRST_NAME);
    }
    
}
