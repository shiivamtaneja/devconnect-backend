package com.shivamtaneja.devconnect.dto.profile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfile {
  @NotNull(message = "Bio cannot be blank")
  private String bio;
  @NotNull(message = "Github cannot be blank")
  private String githubUrl;
  @NotNull(message = "Linkedin cannot be blank")
  private String linkedinUrl;
}
