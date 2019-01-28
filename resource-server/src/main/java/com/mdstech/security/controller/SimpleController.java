package com.mdstech.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class SimpleController {

    @GetMapping("/api/v1/status")
    public String getStatus() {
        return "Response "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
