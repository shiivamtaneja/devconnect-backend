package com.shivamtaneja.devconnect.dto.profile;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfile {
  @NotNull(message = "Name can not be blank")
  private String name;

  private String bio;
  private String githubUrl;
  private String linkedinUrl;

  @NotNull(message = "Email cannot be blank")
  @Email(message = "Please provide a valid email address")
  private String email;

  @NotNull(message = "Password cannot be blank")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;
}
