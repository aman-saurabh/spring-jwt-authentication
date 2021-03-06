package com.technopassel.SpringJwtAuth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping("/hello")
    public String home() {
        return ("<h1>Welcome</h1>");
    }
}
