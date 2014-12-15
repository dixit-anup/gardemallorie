// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.BabySitterService;
import com.appspot.gardemallorie.service.BabySittingService;
import com.appspot.gardemallorie.service.LocationService;

privileged aspect BabySittingController_Roo_Controller {
    
    @Autowired
    BabySitterService BabySittingController.babySitterService;
    
    @Autowired
    BabySittingService BabySittingController.babySittingService;
    
    @Autowired
    LocationService BabySittingController.locationService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String BabySittingController.create(@Valid BabySitting babySitting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, babySitting);
            return "babysittings/create";
        }
        uiModel.asMap().clear();
        babySittingService.saveBabySitting(babySitting);
        return "redirect:/babysittings/" + encodeUrlPathSegment(babySitting.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String BabySittingController.createForm(Model uiModel) {
        populateEditForm(uiModel, new BabySitting());
        return "babysittings/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String BabySittingController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("babysitting", babySittingService.findBabySitting(id));
        uiModel.addAttribute("itemId", id);
        return "babysittings/show";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String BabySittingController.update(@Valid BabySitting babySitting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, babySitting);
            return "babysittings/update";
        }
        uiModel.asMap().clear();
        babySittingService.updateBabySitting(babySitting);
        return "redirect:/babysittings/" + encodeUrlPathSegment(babySitting.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String BabySittingController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, babySittingService.findBabySitting(id));
        return "babysittings/update";
    }
    
    void BabySittingController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("babySitting_day_date_format", "yyyy-MM-dd");
        uiModel.addAttribute("babySitting_plannedbeginning_date_format", "HH:mm");
        uiModel.addAttribute("babySitting_plannedend_date_format", "HH:mm");
        uiModel.addAttribute("babySitting_declaredend_date_format", "HH:mm");
        uiModel.addAttribute("babySitting_chargedend_date_format", "HH:mm");
    }
    
    void BabySittingController.populateEditForm(Model uiModel, BabySitting babySitting) {
        uiModel.addAttribute("babySitting", babySitting);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("babysitters", babySitterService.findAllBabySitters());
        uiModel.addAttribute("locations", locationService.findAllLocations());
    }
    
    String BabySittingController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
