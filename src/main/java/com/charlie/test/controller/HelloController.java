package com.charlie.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/hi")
    public String hello(Model model) {
        model.addAttribute("name", "jac");
        return "hello";
    }
}
