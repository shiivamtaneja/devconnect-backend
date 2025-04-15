package com.shivamtaneja.devconnect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shivamtaneja.devconnect.dto.auth.LoginRequest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
  @PostMapping("/login")
  public String loginMethod(@RequestBody @Valid LoginRequest body) {
    log.info("Body is {}", body);
    return "Ok";
  }
}
