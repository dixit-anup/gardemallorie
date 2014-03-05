package com.appspot.gardemallorie.web;
import com.appspot.gardemallorie.domain.BabySitting;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/babysittings")
@Controller
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
public class BabySittingController {
}
