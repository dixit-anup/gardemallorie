package com.appspot.gardemallorie.web;
import com.appspot.gardemallorie.domain.Localisation;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;

@RooWebJson(jsonObject = Localisation.class)
@Controller
@RequestMapping("/localisations")
@RooWebScaffold(path = "localisations", formBackingObject = Localisation.class)
public class LocalisationController {
}
