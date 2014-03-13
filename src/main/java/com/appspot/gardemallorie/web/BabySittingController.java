package com.appspot.gardemallorie.web;

import java.util.Date;

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
	
	public static final String BABYSITTINGS_ATTR = "babysittings";
	public static final String BABYSITTINGS_LIST_VIEW = BABYSITTINGS_ATTR + "/list";
	public static final String DEFAULT_SORT_FIELD = "day";
	public static final String DEFAULT_PAGE_SIZE = "100";
	public static final String MAX_PAGE_ATTR = "maxPages";
	public static final String PAGE_PARAM = "page";
	public static final String PAGE_SIZE_PARAM = "size";
	public static final String SORT_FIELD_PARAM = "sortField";
	public static final String SORT_ORDER_PARAM = "sortOrder";

    @RequestMapping(params = "find=Next")
    public String findNextBabySittings(
		@RequestParam(value = PAGE_PARAM, required = false) Integer page,
		@RequestParam(value = PAGE_SIZE_PARAM, required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
		@RequestParam(value = SORT_FIELD_PARAM, required = false) String sortFieldName,
		@RequestParam(value = SORT_ORDER_PARAM, required = false) String sortOrder,
		Model uiModel)
    {
    	Date day = new Date();
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(BABYSITTINGS_ATTR, BabySitting.findBabySittingsByDayGreaterThanEquals(day, sortFieldName, sortOrder).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
            float nrOfPages = (float) BabySitting.countFindBabySittingsByDayGreaterThanEquals(day) / sizeNo;
            uiModel.addAttribute(MAX_PAGE_ATTR, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute(BABYSITTINGS_ATTR, BabySitting.findBabySittingsByDayGreaterThanEquals(day, sortFieldName, sortOrder).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return BABYSITTINGS_LIST_VIEW;
    }
    
    @RequestMapping(produces = "text/html")
    public String list(
		@RequestParam(value = PAGE_PARAM, required = false) Integer page,
		@RequestParam(value = PAGE_SIZE_PARAM, required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
		@RequestParam(value = SORT_FIELD_PARAM, required = false, defaultValue = DEFAULT_SORT_FIELD) String sortFieldName,
		@RequestParam(value = SORT_ORDER_PARAM, required = false) String sortOrder,
		Model uiModel)
    {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(BABYSITTINGS_ATTR, BabySitting.findBabySittingEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) BabySitting.countBabySittings() / sizeNo;
            uiModel.addAttribute(MAX_PAGE_ATTR, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute(BABYSITTINGS_ATTR, BabySitting.findAllBabySittings(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return BABYSITTINGS_LIST_VIEW;
    }
    
}
