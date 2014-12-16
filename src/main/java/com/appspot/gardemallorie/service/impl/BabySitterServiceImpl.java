package com.appspot.gardemallorie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.repository.BabySitterRepository;
import com.appspot.gardemallorie.service.BabySitterService;

@Service
public class BabySitterServiceImpl implements BabySitterService {

	static private Sort SORT_BY_FIRST_NAME = new Sort("firstName");

	@Autowired
    BabySitterRepository babySitterRepository;
	
	@Override
	@Transactional
	public void deleteBabySitter(Long id) {
		babySitterRepository.delete(id);
	}
	
	@Override
    public List<BabySitter> findAllBabySitters() {
        return babySitterRepository.findAll(SORT_BY_FIRST_NAME);
    }
    
	@Override
	public Page<BabySitter> findAllBabySitters(Pageable pageable) {
        return babySitterRepository.findAll(pageable);
    }
    
	@Override
    public BabySitter findBabySitter(Long id) {
        return babySitterRepository.findOne(id);
    }
    
	@Override
	@Transactional
    public void saveBabySitter(BabySitter babySitter) {
        babySitterRepository.save(babySitter);
    }
    
	@Override
	@Transactional
    public BabySitter updateBabySitter(BabySitter babySitter) {
        return babySitterRepository.save(babySitter);
    }
    
}
