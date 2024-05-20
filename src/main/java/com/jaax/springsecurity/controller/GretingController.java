package com.jaax.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GretingController {

    @GetMapping("/hello")
    public String greet() {
        return "Hello, Mundo!";
    }

    @GetMapping("/helloProtected")
    public String greetProtected() {
        return "Hello, Mundo! (protegido)";
    }

}
