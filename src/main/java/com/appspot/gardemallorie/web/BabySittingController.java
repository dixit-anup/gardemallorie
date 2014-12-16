package com.appspot.gardemallorie.web;

import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appspot.gardemallorie.domain.BabySitting;

@Controller
@RequestMapping("/babysittings")
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
public class BabySittingController {

	static private final String BABYSITTING_EXTRACHARGES_VIEW = "babysittings/extraCharges";
	static private final String BABYSITTING_LIST_VIEW = "babysittings/list";
	static private final String INDEX_VIEW = REDIRECT_URL_PREFIX + '/';

	@RequestMapping(method = RequestMethod.PUT, params = "copyUntil", produces = "text/html")
	public String copyUntil(@RequestParam("copyUntil") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, BabySitting babySitting, Model uiModel) {
        
		babySittingService.copyBabySittingUntil(date, babySitting.getId());
        
        uiModel.asMap().clear();
        
        return INDEX_VIEW;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        
        babySittingService.deleteBabySitting(id);
        
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "0" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        
        return "redirect:/babysittings";
    }
    
    @RequestMapping(params = "find=ExtraCharges")
    public String findExtraCharges(Model uiModel) {

        uiModel.addAttribute("extraCharges", babySittingService.findExtraCharges());

        addDateTimeFormatPatterns(uiModel);
        
        return BABYSITTING_EXTRACHARGES_VIEW;
    }

	@RequestMapping(params = "find=Next")
	public String findNextBabySittings(Pageable pageable, Model uiModel, HttpServletResponse response)
	{
		response.setHeader("Cache-Control", "private, max-age=0, no-cache");
		
		Page<BabySitting> babySittings = babySittingService.findNextBabySittings(pageable);

		uiModel.addAttribute("babysittings", babySittings.getContent());
        uiModel.addAttribute("maxPages", babySittings.getTotalPages());
        addDateTimeFormatPatterns(uiModel);
        
        return BABYSITTING_LIST_VIEW;
    }

    @RequestMapping(produces = "text/html")
    public String list(Pageable pageable, Model uiModel) {

    	Page<BabySitting> babySittings = babySittingService.findAllBabySittings(pageable);

    	uiModel.addAttribute("babysittings", babySittings.getContent());
        uiModel.addAttribute("maxPages", babySittings.getTotalPages());
        addDateTimeFormatPatterns(uiModel);

        return BABYSITTING_LIST_VIEW;
    }
    
}
