// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.domain;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.domain.Location;
import java.util.Date;

privileged aspect BabySitting_Roo_JavaBean {
    
    public Date BabySitting.getDay() {
        return this.day;
    }
    
    public void BabySitting.setDay(Date day) {
        this.day = day;
    }
    
    public BabySitter BabySitting.getBabySitter() {
        return this.babySitter;
    }
    
    public void BabySitting.setBabySitter(BabySitter babySitter) {
        this.babySitter = babySitter;
    }
    
    public Date BabySitting.getPlannedBeginning() {
        return this.plannedBeginning;
    }
    
    public void BabySitting.setPlannedBeginning(Date plannedBeginning) {
        this.plannedBeginning = plannedBeginning;
    }
    
    public BabySitter BabySitting.getGo() {
        return this.go;
    }
    
    public void BabySitting.setGo(BabySitter go) {
        this.go = go;
    }
    
    public Date BabySitting.getPlannedEnd() {
        return this.plannedEnd;
    }
    
    public void BabySitting.setPlannedEnd(Date plannedEnd) {
        this.plannedEnd = plannedEnd;
    }
    
    public BabySitter BabySitting.getBack() {
        return this.back;
    }
    
    public void BabySitting.setBack(BabySitter back) {
        this.back = back;
    }
    
    public String BabySitting.getComment() {
        return this.comment;
    }
    
    public void BabySitting.setComment(String comment) {
        this.comment = comment;
    }
    
    public Location BabySitting.getLocation() {
        return this.location;
    }
    
    public void BabySitting.setLocation(Location location) {
        this.location = location;
    }
    
    public Date BabySitting.getDeclaredEnd() {
        return this.declaredEnd;
    }
    
    public void BabySitting.setDeclaredEnd(Date declaredEnd) {
        this.declaredEnd = declaredEnd;
    }
    
    public Date BabySitting.getChargedEnd() {
        return this.chargedEnd;
    }
    
    public void BabySitting.setChargedEnd(Date chargedEnd) {
        this.chargedEnd = chargedEnd;
    }
    
    public float BabySitting.getExtraHours() {
        return this.extraHours;
    }
    
    public void BabySitting.setExtraHours(float extraHours) {
        this.extraHours = extraHours;
    }
    
    public float BabySitting.getChildcareVouchers() {
        return this.childcareVouchers;
    }
    
    public void BabySitting.setChildcareVouchers(float childcareVouchers) {
        this.childcareVouchers = childcareVouchers;
    }
    
    public Date BabySitting.getCopyUntil() {
        return this.copyUntil;
    }
    
    public void BabySitting.setCopyUntil(Date copyUntil) {
        this.copyUntil = copyUntil;
    }
    
}