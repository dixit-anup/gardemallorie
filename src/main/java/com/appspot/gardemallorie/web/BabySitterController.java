package com.appspot.gardemallorie.web;
import com.appspot.gardemallorie.domain.BabySitter;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;

@RooWebJson(jsonObject = BabySitter.class)
@Controller
@RequestMapping("/babysitters")
@RooWebScaffold(path = "babysitters", formBackingObject = BabySitter.class)
public class BabySitterController {
}
