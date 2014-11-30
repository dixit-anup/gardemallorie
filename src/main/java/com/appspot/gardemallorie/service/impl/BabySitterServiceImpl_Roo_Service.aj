// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.service.impl;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.repository.BabySitterRepository;
import com.appspot.gardemallorie.service.impl.BabySitterServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect BabySitterServiceImpl_Roo_Service {
    
    declare @type: BabySitterServiceImpl: @Service;
    
    declare @type: BabySitterServiceImpl: @Transactional;
    
    @Autowired
    BabySitterRepository BabySitterServiceImpl.babySitterRepository;
    
    public long BabySitterServiceImpl.countAllBabySitters() {
        return babySitterRepository.count();
    }
    
    public void BabySitterServiceImpl.deleteBabySitter(BabySitter babySitter) {
        babySitterRepository.delete(babySitter);
    }
    
    public BabySitter BabySitterServiceImpl.findBabySitter(Long id) {
        return babySitterRepository.findOne(id);
    }
    
    public List<BabySitter> BabySitterServiceImpl.findAllBabySitters() {
        return babySitterRepository.findAll();
    }
    
    public List<BabySitter> BabySitterServiceImpl.findBabySitterEntries(int firstResult, int maxResults) {
        return babySitterRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public void BabySitterServiceImpl.saveBabySitter(BabySitter babySitter) {
        babySitterRepository.save(babySitter);
    }
    
    public BabySitter BabySitterServiceImpl.updateBabySitter(BabySitter babySitter) {
        return babySitterRepository.save(babySitter);
    }
    
}