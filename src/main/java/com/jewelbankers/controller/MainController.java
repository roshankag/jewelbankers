package com.jewelbankers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectToIndex() {
        return "forward:/browser/index.html"; // Forward to index.html
    }
}
