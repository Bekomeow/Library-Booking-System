package com.example.SpringBoot_project_2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String home() {
        return "homePage/home";
    }
}
