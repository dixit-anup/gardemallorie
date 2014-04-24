// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.appspot.gardemallorie.web;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.web.BabySitterController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect BabySitterController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String BabySitterController.create(@Valid BabySitter babySitter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, babySitter);
            return "babysitters/create";
        }
        uiModel.asMap().clear();
        babySitter.persist();
        return "redirect:/babysitters/" + encodeUrlPathSegment(babySitter.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String BabySitterController.createForm(Model uiModel) {
        populateEditForm(uiModel, new BabySitter());
        return "babysitters/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String BabySitterController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("babysitter", BabySitter.findBabySitter(id));
        uiModel.addAttribute("itemId", id);
        return "babysitters/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String BabySitterController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("babysitters", BabySitter.findBabySitterEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) BabySitter.countBabySitters() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("babysitters", BabySitter.findAllBabySitters(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "babysitters/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String BabySitterController.update(@Valid BabySitter babySitter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, babySitter);
            return "babysitters/update";
        }
        uiModel.asMap().clear();
        babySitter.merge();
        return "redirect:/babysitters/" + encodeUrlPathSegment(babySitter.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String BabySitterController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, BabySitter.findBabySitter(id));
        return "babysitters/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String BabySitterController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        BabySitter babySitter = BabySitter.findBabySitter(id);
        babySitter.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/babysitters";
    }
    
    void BabySitterController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("babySitter_extrahoursbeginning_date_format", "HH:mm");
    }
    
    void BabySitterController.populateEditForm(Model uiModel, BabySitter babySitter) {
        uiModel.addAttribute("babySitter", babySitter);
        addDateTimeFormatPatterns(uiModel);
    }
    
    String BabySitterController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
