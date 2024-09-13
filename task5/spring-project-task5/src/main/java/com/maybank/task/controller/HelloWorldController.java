package com.maybank.task.controller;

import com.maybank.task.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/hello")
    public String hello(Model model) {
        String message = messageService.getMessage();
        model.addAttribute("message", message);
        return "index";
    }
}
