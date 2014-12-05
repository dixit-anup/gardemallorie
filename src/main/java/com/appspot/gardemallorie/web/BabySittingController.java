package com.appspot.gardemallorie.web;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.BabySittingService;

@Controller
@RequestMapping("/babysittings")
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
public class BabySittingController {

	static private final String INDEX_VIEW = REDIRECT_URL_PREFIX + '/';
	static private final String BABYSITTING_EXTRACHARGES_VIEW = "babysittings/extraCharges";
	static private final String BABYSITTING_LIST_VIEW = "babysittings/list";

    @Autowired
    private BabySittingService babySittingService;
    
	@RequestMapping(method = RequestMethod.PUT, params = "copyUntil", produces = "text/html")
	public String copyUntil(
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date copyUntil,
		BabySitting b,
		BindingResult bindingResult,
		Model uiModel)
	{
    	BabySitting babySitting = babySittingService.findBabySitting(b.getId());
    	
    	if (babySittingService.countBabySittingsByDayGreaterThanEquals(babySitting.getDay()) > 1) {
            return "babysittings/update";
    	}

        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(babySitting.getDay());

        Calendar copyUntilDay = Calendar.getInstance();
        copyUntilDay.setTime(copyUntil);
        
        while (currentDay.compareTo(copyUntilDay) <= 0) {
        	
        	currentDay.add(DAY_OF_MONTH, 1);
        	int currentDayOfWeek = currentDay.get(DAY_OF_WEEK);

        	// Skip saturdays and sundays
        	if (currentDayOfWeek == SATURDAY || currentDayOfWeek == SUNDAY) {
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
        	babySittingService.saveBabySitting(copy);
        }
        
        uiModel.asMap().clear();
        
        return INDEX_VIEW;
    }
    
    @RequestMapping(params = "find=ExtraCharges")
    public String findExtraCharges(Model uiModel) {

    	List<BabySitting> babySittings = babySittingService.findAllBabySittingsOrderByDay();
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
        
        return BABYSITTING_EXTRACHARGES_VIEW;
    }

	@RequestMapping(params = "find=Next")
	public String findNextBabySittings(Pageable pageable, Model uiModel, HttpServletResponse response)
	{
		response.setHeader("Cache-Control", "private, max-age=0, no-cache");
		
		int size = pageable.getPageSize();
		float nrOfPages = (float) babySittingService.countNextBabySittings() / size;
		List<BabySitting> babySittings = babySittingService.findNextBabySittings(pageable);

		uiModel.addAttribute("babysittings", babySittings);
        uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        
        addDateTimeFormatPatterns(uiModel);
        
        return BABYSITTING_LIST_VIEW;
    }

    @RequestMapping(produces = "text/html")
    public String list(Pageable pageable, Model uiModel) {

		int size = pageable.getPageSize();
		float nrOfPages = (float) babySittingService.countAllBabySittings() / size;
		List<BabySitting> babySittings = babySittingService.findAllBabySittings(pageable);

		uiModel.addAttribute("babysittings", babySittings);
        uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));

        addDateTimeFormatPatterns(uiModel);

        return BABYSITTING_LIST_VIEW;
    }
    
}
