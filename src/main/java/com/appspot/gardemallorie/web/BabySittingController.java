package com.appspot.gardemallorie.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
 * TODO: send emails using AspectJ
 * @author DAVID
 *
 */
@Controller
@RequestMapping("/babysittings")
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
@RooWebFinder
public class BabySittingController {

	@Autowired
    private transient MailSender mailSender;

    @Autowired
    @Qualifier("saveBabySittingMailMessage")
    private transient SimpleMailMessage saveBabySittingMailMessage;

    @Autowired
    @Qualifier("updateBabySittingMailMessage")
    private transient SimpleMailMessage updateBabySittingMailMessage;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid BabySitting babySitting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, babySitting);
            return "babysittings/create";
        }
        uiModel.asMap().clear();
        babySitting.persist();
        // email start 
    	sendMailMessage(saveBabySittingMailMessage, babySitting);
        // email stop 
        return "redirect:/babysittings/" + encodeUrlPathSegment(babySitting.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid BabySitting babySitting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, babySitting);
            return "babysittings/update";
        }
        uiModel.asMap().clear();
        // email start 
    	BabySitting actual = BabySitting.findBabySitting(babySitting.getId());
    	Set<BabySitter> babySitters = new HashSet<BabySitter>(6);
    	babySitters.add(actual.getBabySitter());
    	babySitters.add(actual.getBack());
    	babySitters.add(actual.getGo());
        // email stop
        babySitting.merge();
        // email start 
    	sendMailMessage(updateBabySittingMailMessage, babySitting, babySitters);
        // email stop 
        return "redirect:/babysittings/" + encodeUrlPathSegment(babySitting.getId().toString(), httpServletRequest);
    }
    
    void sendMailMessage(SimpleMailMessage templateMessage, BabySitting babySitting, Set<BabySitter> babySitters) {
    	
    	babySitters.add(babySitting.getBabySitter());
    	babySitters.add(babySitting.getBack());
    	babySitters.add(babySitting.getGo());
    	List<String> to = new ArrayList<String>();

    	for (BabySitter b : babySitters) {
    		if (b != null && b.isNotification() && b.getEmail() != null) {
    			to.add(b.getEmail());
    		}
    	}

    	if (to.size() > 0) {
	        SimpleMailMessage mailMessage = new SimpleMailMessage(templateMessage);
	        mailMessage.setTo(to.toArray(new String[to.size()]));
	        StringBuilder text = new StringBuilder();
	        text.append("Date :\t").append(babySitting.getDay());
	        text.append("\r\nBaby-sitter :\t").append(babySitting.getBabySitter().getFirstName());
	        text.append("\r\nDébut prévu :\t").append(babySitting.getPlannedBeginning());
	        text.append("\r\nAller :\t").append(babySitting.getGo().getFirstName());
	        text.append("\r\nFin prévue :\t").append(babySitting.getPlannedEnd());
	        text.append("\r\nRetour :\t").append(babySitting.getBack().getFirstName());
	        text.append("\r\nCommentaire :\t").append(babySitting.getComment());
	        text.append("\r\nLieu :\t").append(babySitting.getLocation().getName());
	        mailMessage.setText(text.toString());
	        mailSender.send(mailMessage);
    	}

    }
    
    void sendMailMessage(SimpleMailMessage templateMessage, BabySitting babySitting) {
    	sendMailMessage(templateMessage, babySitting, new HashSet<BabySitter>(3));
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

    @RequestMapping(params = "find=ExtraHours")
    public String findExtraHours(Model uiModel) {
        List<BabySitting> babySittings = BabySitting.findAllBabySittings("day", "asc");
        List<BabySitting> extraHoursList = new ArrayList<BabySitting>();
        BabySitting currentExtraHours = null;
        for (int i = 0; i < babySittings.size(); i++) {
            BabySitting babySitting = babySittings.get(i);
            Date babySittingDay = babySitting.getDay();
            if (i == 0 || babySittingDay.getYear() != currentExtraHours.getDay().getYear() || babySittingDay.getMonth() != currentExtraHours.getDay().getMonth()) {
                currentExtraHours = new BabySitting();
                currentExtraHours.setDay((Date) babySittingDay.clone());
                currentExtraHours.getDay().setDate(1);
                currentExtraHours.setExtraHours(babySitting.getExtraHours());
                extraHoursList.add(currentExtraHours);
            } else {
                currentExtraHours.setExtraHours(currentExtraHours.getExtraHours() + babySitting.getExtraHours());
            }
        }
        uiModel.addAttribute("extraHours", extraHoursList);
        addDateTimeFormatPatterns(uiModel);
        return "babysittings/extraHours";
    }

}
