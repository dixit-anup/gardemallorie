// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.service;

import java.util.List;

import com.appspot.gardemallorie.domain.BabySitter;

privileged aspect BabySitterService_Roo_Service {
    
    public abstract long BabySitterService.countAllBabySitters();    
    public abstract void BabySitterService.deleteBabySitter(BabySitter babySitter);    
    public abstract BabySitter BabySitterService.findBabySitter(Long id);    
    public abstract List<BabySitter> BabySitterService.findAllBabySitters();    
    public abstract List<BabySitter> BabySitterService.findBabySitterEntries(int firstResult, int maxResults);    
    public abstract void BabySitterService.saveBabySitter(BabySitter babySitter);    
    public abstract BabySitter BabySitterService.updateBabySitter(BabySitter babySitter);    
}
