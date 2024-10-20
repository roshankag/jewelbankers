package com.jewelbankers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirectToIndex() {
        return "forward:/index.html"; // Forward to index.html
    }
}
