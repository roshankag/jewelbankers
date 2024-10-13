//package com.jewelbankers.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class WebController {
//
//    // Handle all frontend routes (except API paths)
//    @RequestMapping(value = {"/{path:^(?!jewelbankersapi).*}", "/{path:^(?!jewelbankersapi).*}/**"})
//    public String redirect() {
//        // Return index.html for Angular routes
//        return "forward:/index.html";
//    }
//}
