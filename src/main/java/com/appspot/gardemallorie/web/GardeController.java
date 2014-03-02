package com.appspot.gardemallorie.web;
import com.appspot.gardemallorie.domain.Garde;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;

@RooWebJson(jsonObject = Garde.class)
@Controller
@RequestMapping("/gardes")
@RooWebScaffold(path = "gardes", formBackingObject = Garde.class)
public class GardeController {
}
