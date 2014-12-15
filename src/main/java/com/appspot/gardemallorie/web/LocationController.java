package com.appspot.gardemallorie.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appspot.gardemallorie.domain.Location;

@Controller
@RequestMapping("/locations")
@RooWebScaffold(path = "locations", formBackingObject = Location.class)
public class LocationController {

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, Pageable pageable, Model uiModel) {

        locationService.deleteLocation(id);

        uiModel.asMap().clear();
        uiModel.addAttribute("page", pageable.getPageNumber());
        uiModel.addAttribute("size", pageable.getPageSize());
        if (pageable.getSort() != null) {
            uiModel.addAttribute("sort", pageable.getSort().toString());
        }
        
        return "redirect:/locations";
    }
    
    @RequestMapping(produces = "text/html")
    public String list(Pageable pageable, Model uiModel) {
    	
    	Page<Location> locations = locationService.findAllLocations(pageable);

    	uiModel.addAttribute("locations", locations.getContent());
        uiModel.addAttribute("maxPages", locations.getTotalPages());

        return "locations/list";
    }

}
