package com.appspot.gardemallorie.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appspot.gardemallorie.domain.Location;

@Controller
@RequestMapping("/locations")
@RooWebScaffold(path = "locations", formBackingObject = Location.class)
public class LocationController {

    @RequestMapping(produces = "text/html")
    public String list(Pageable pageable, Model uiModel) {
    	
    	Page<Location> locations = locationService.findAllLocations(pageable);

    	uiModel.addAttribute("locations", locations.getContent());
        uiModel.addAttribute("maxPages", locations.getTotalPages());

        return "locations/list";
    }

}
