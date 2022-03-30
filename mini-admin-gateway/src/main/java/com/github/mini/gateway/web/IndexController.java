package com.github.mini.gateway.web;

import com.github.mini.common.ann.Anonymous;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IndexController {

    @Anonymous
    @GetMapping("/index")
    public String init() {
        return "Welcome to use Mini Admin Sys!";
    }
}
