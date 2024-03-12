package com.bbdgrad.beanfinancetrackerserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("")
public class HelloWorld {
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from our API");
    }

}
