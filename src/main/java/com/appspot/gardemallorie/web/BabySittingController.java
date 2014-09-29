package com.appspot.gardemallorie.web;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.domain.BabySitting;

/**
 * @author DAVID
 *
 */
@Controller
@RequestMapping("/babysittings")
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
@RooWebFinder
public class BabySittingController {

    @RequestMapping(method = RequestMethod.PUT, params = "copyUntil", produces = "text/html")
    public String copyUntil(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date copyUntil, BabySitting b, BindingResult bindingResult, Model uiModel) {

    	BabySitting babySitting = BabySitting.findBabySitting(b.getId());
    	
    	if (BabySitting.countFindBabySittingsByDayGreaterThanEquals(babySitting.getDay()) > 1) {
            return "babysittings/update";
    	}

        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(babySitting.getDay());

        Calendar copyUntilDay = Calendar.getInstance();
        copyUntilDay.setTime(copyUntil);
        
        while (currentDay.compareTo(copyUntilDay) <= 0) {
        	
        	currentDay.add(Calendar.DAY_OF_MONTH, 1);
        	int currentDayOfWeek = currentDay.get(Calendar.DAY_OF_WEEK);

        	// Skip saturdays and sundays
        	if (currentDayOfWeek == Calendar.SATURDAY || currentDayOfWeek == Calendar.SUNDAY) {
        		continue;
        	}
        	
        	BabySitting copy = new BabySitting();
        	copy.setBabySitter(babySitting.getBabySitter());
        	copy.setBack(babySitting.getBack());
        	copy.setDay(currentDay.getTime());
        	copy.setGo(babySitting.getGo());
        	copy.setLocation(babySitting.getLocation());
        	copy.setPlannedBeginning(babySitting.getPlannedBeginning());
        	copy.setPlannedEnd(babySitting.getPlannedEnd());
            copy.persist();
        }
        
        uiModel.asMap().clear();
        
        return findNextBabySittings(null, null, null, null, uiModel);
    }
    
    @RequestMapping(params = "find=ByDayAndBabySitter")
    public String findBabySittingsByDayAndBabySitter(
			@RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date day,
			@RequestParam(value = "babySitter", required = false) Long babySitter,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel
		)
    {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("babysittings", BabySitting.findBabySittingsByDayAndBabySitter(day, babySitter).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
            float nrOfPages = (float) BabySitting.countFindBabySittingsByDayAndBabySitter(day, babySitter) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("babysittings", BabySitting.findBabySittingsByDayAndBabySitter(day, babySitter).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "babysittings/list";
    }
    
    
    @RequestMapping(params = {"find=ByDayAndBabySitter", "form"})
    public String findBabySittingsByDayAndBabySitterForm(Model uiModel) {
        uiModel.addAttribute("babysitters", BabySitter.findAllBabySitters());
        return "babysittings/findBabySittingsByDayAndBabySitter";
    }
    
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

    @RequestMapping(params = "find=ExtraCharges")
    public String findExtraCharges(Model uiModel) {

    	List<BabySitting> babySittings = BabySitting.findAllBabySittings("day", "asc");
        List<BabySitting> extraChargesList = new ArrayList<BabySitting>();
        BabySitting extraCharges = null;
        
        for (int i = 0; i < babySittings.size(); i++) {
        
        	BabySitting babySitting = babySittings.get(i);
            Date babySittingDay = babySitting.getDay();
            
            if (i == 0 || babySittingDay.getYear() != extraCharges.getDay().getYear() || babySittingDay.getMonth() != extraCharges.getDay().getMonth()) {
                extraCharges = new BabySitting();
                extraCharges.setDay((Date) babySittingDay.clone());
                extraCharges.getDay().setDate(1);
                extraChargesList.add(extraCharges);
                // Childcare vouchers
                extraCharges.setChildcareVouchers(babySitting.getChildcareVouchers());
                // Extra hours
                extraCharges.setExtraHours(babySitting.getExtraHours());
            } else {
                // Childcare vouchers
                extraCharges.setChildcareVouchers(extraCharges.getChildcareVouchers() + babySitting.getChildcareVouchers());
                // Extra hours
                extraCharges.setExtraHours(extraCharges.getExtraHours() + babySitting.getExtraHours());
            }
        }

        uiModel.addAttribute("extraCharges", extraChargesList);
        addDateTimeFormatPatterns(uiModel);
        return "babysittings/extraCharges";
    }

}
