package com.oms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @GetMapping("/home")
    public String getHello(){
        return "Witaj home!";
    }

    @GetMapping("/users")
    public String getUsers(){
        return "Witaj users!";
    }
}
