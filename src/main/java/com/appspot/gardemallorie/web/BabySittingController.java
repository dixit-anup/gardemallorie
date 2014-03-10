package com.appspot.gardemallorie.web;
import java.util.Date;

import com.appspot.gardemallorie.domain.BabySitting;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;

@RequestMapping("/babysittings")
@Controller
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
@RooWebFinder
public class BabySittingController {

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("babySitting_day_date_format", "EEE dd/MM/yyyy");
        uiModel.addAttribute("babySitting_plannedbeginning_date_format", "HH:mm");
        uiModel.addAttribute("babySitting_plannedend_date_format", "HH:mm");
        uiModel.addAttribute("babySitting_declaredend_date_format", "HH:mm");
    }
    
    @RequestMapping(value = "/next", produces = "text/html")
    public String findNextBabySittings(@RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        int sizeNo = 200;
        final int firstResult = 0;
        Date day = new Date();
        uiModel.addAttribute("babysittings", BabySitting.findBabySittingsByDayGreaterThanEquals(day, sortFieldName, sortOrder).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
        float nrOfPages = (float) BabySitting.countFindBabySittingsByDayGreaterThanEquals(day) / sizeNo;
        uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        addDateTimeFormatPatterns(uiModel);
        return "babysittings/list";
    }
    
}
