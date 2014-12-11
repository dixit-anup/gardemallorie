package com.appspot.gardemallorie.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appspot.gardemallorie.domain.BabySitter;

@Controller
@RequestMapping("/babysitters")
@RooWebScaffold(path = "babysitters", formBackingObject = BabySitter.class)
public class BabySitterController {

    @RequestMapping(produces = "text/html")
    public String list(Pageable pageable, Model uiModel) {
    	
    	Page<BabySitter> babySitters = babySitterService.findAllBabySitters(pageable);

    	uiModel.addAttribute("babysitters", babySitters.getContent());
        uiModel.addAttribute("maxPages", babySitters.getTotalPages());
        addDateTimeFormatPatterns(uiModel);

        return "babysitters/list";
    }
    
}
