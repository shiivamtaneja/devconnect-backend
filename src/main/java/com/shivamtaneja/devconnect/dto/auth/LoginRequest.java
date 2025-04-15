package com.shivamtaneja.devconnect.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
  @NotNull(message = "Email cannot be blank")
  @Email(message = "Please provide a valid email address")
  private String email;

  @NotNull(message = "Password cannot be blank")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;
}
