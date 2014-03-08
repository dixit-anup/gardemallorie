package com.appspot.gardemallorie.web;
import com.appspot.gardemallorie.domain.BabySitting;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;

@RequestMapping("/babysittings")
@Controller
@RooWebScaffold(path = "babysittings", formBackingObject = BabySitting.class)
@RooWebFinder
public class BabySittingController {
}
