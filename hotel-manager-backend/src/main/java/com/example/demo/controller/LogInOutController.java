package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogInOutController {


    @RequestMapping("/secured")
    public ResponseEntity<String> secured(){
        return ResponseEntity.ok("DUPA");
    }

    @RequestMapping("/fail")
    public  ResponseEntity<String> failLogin(){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Bad credentials");
    }


}
