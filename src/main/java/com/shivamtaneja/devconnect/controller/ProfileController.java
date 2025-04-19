package com.shivamtaneja.devconnect.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shivamtaneja.devconnect.common.response.ApiResponse;
import com.shivamtaneja.devconnect.dto.profile.CreateProfile;
import com.shivamtaneja.devconnect.dto.profile.ProfileResponse;
import com.shivamtaneja.devconnect.dto.profile.UpdateProfile;
import com.shivamtaneja.devconnect.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
  private final ProfileService profileService;

  @PostMapping()
  public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(@RequestBody @Valid CreateProfile profileDTO) {
    ProfileResponse newProfile = profileService.createProfile(profileDTO);

    ApiResponse<ProfileResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), newProfile, null, true);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@PathVariable("id") String profileID,
      @RequestBody @Valid UpdateProfile profileDTO) {
    ProfileResponse updatedProfile = profileService.updateProfile(profileID, profileDTO);

    ApiResponse<ProfileResponse> response = new ApiResponse<>(HttpStatus.OK.value(), updatedProfile, null, true);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable("id") String profileID) {
    ProfileResponse updatedProfile = profileService.getProfile(profileID);

    ApiResponse<ProfileResponse> response = new ApiResponse<>(HttpStatus.OK.value(), updatedProfile, null, true);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteProfile(@PathVariable("id") String profileID) {
    profileService.deleteProfile(profileID);

    ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(),null, null, true);

    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }
}
