package com.awb.MyLibrary.controllers;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile("h2")
public class MainController {


    @GetMapping({"", "/"})
    public String home() {
        return "index";
    }

}