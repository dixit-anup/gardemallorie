package com.appspot.gardemallorie.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appspot.gardemallorie.domain.BabySitter;

@RequestMapping("/babysitters")
@Controller
@RooWebScaffold(path = "babysitters", formBackingObject = BabySitter.class)
public class BabySitterController {
}
