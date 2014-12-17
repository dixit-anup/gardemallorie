package com.appspot.gardemallorie.web;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appspot.gardemallorie.domain.Location;

@Controller
@RequestMapping(produces = TEXT_HTML_VALUE, value = "/locations")
@RooWebScaffold(path = "locations", formBackingObject = Location.class)
public class LocationController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = DELETE, value = "/{id}")
    public String delete(@PathVariable("id") Long id, Model uiModel, Pageable pageable) {

        locationService.deleteLocation(id);
        
        logger.debug("uiModel.class: {}, uiModel: {}", uiModel.getClass(), uiModel);

        uiModel.asMap().clear();
        uiModel.addAttribute("page", pageable.getPageNumber());
        uiModel.addAttribute("size", pageable.getPageSize());
        if (pageable.getSort() != null) {
            uiModel.addAttribute("sort", pageable.getSort().toString());
        }
        
        return "redirect:/locations";
    }
    
    @RequestMapping
    public String list(Model uiModel, Pageable pageable) {
    	
    	Page<Location> page = locationService.findAllLocations(pageable);

    	uiModel.addAttribute("locations", page.getContent());
        uiModel.addAttribute("maxPages", page.getTotalPages());

        return "locations/list";
    }

}
