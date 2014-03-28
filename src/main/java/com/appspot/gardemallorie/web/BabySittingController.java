package com.appspot.gardemallorie.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.appspot.gardemallorie.domain.BabySitting;

@Controller
@RequestMapping("/babysittings")
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
@RooWebFinder
public class BabySittingController {
	
    @RequestMapping(params = "find=Next")
    public String findNextBabySittings(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
    	Date day = new Date();
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("babysittings", BabySitting.findBabySittingsByDayGreaterThanEquals(day, sortFieldName, sortOrder).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
            float nrOfPages = (float) BabySitting.countFindBabySittingsByDayGreaterThanEquals(day) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("babysittings", BabySitting.findBabySittingsByDayGreaterThanEquals(day, sortFieldName, sortOrder).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "babysittings/list";
    }

    @RequestMapping(params = "find=ExtraHours")
    public String findExtraHours(Model uiModel) {

		List<BabySitting> babySittings = BabySitting.findAllBabySittings("day", "asc");
		List<BabySitting> extraHoursList = new ArrayList<BabySitting>();
		BabySitting currentExtraHours = null;
		
		for (int i = 0; i< babySittings.size(); i++) {
		
			BabySitting babySitting = babySittings.get(i);
			Date babySittingDay = babySitting.getDay();
			
			if (i == 0
				|| babySittingDay.getYear() != currentExtraHours.getDay().getYear()
				|| babySittingDay.getMonth() != currentExtraHours.getDay().getMonth()
			) {
				currentExtraHours = new BabySitting();
				currentExtraHours.setDay((Date) babySittingDay.clone());
				currentExtraHours.getDay().setDate(1);
				currentExtraHours.setExtraHours(babySitting.getExtraHours());
				extraHoursList.add(currentExtraHours);
			}
			else {
				currentExtraHours.setExtraHours(currentExtraHours.getExtraHours() + babySitting.getExtraHours());
			}
		}

		uiModel.addAttribute("extraHours", extraHoursList);

        addDateTimeFormatPatterns(uiModel);

        return "babysittings/extraHours";
    }
}
