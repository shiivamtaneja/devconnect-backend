package com.shivamtaneja.devconnect.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth Controller")
public class AuthController {
  @PostMapping("/login")
  public String loginMethod(@RequestBody @Valid LoginRequest body) {
    log.info("Body is {}", body);
    return "Ok";
  }
}
