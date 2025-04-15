package com.shivamtaneja.devconnect.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/user")
public class UserController {
  @GetMapping()
  public String getMethodName() {
      return new String("Hello my friend");
  }
  
}
