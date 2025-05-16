package com.shivamtaneja.devconnect.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.shivamtaneja.devconnect.common.response.ApiResponse;
import com.shivamtaneja.devconnect.dto.profile.CreateProfile;
import com.shivamtaneja.devconnect.dto.profile.ProfileResponse;
import com.shivamtaneja.devconnect.dto.profile.UpdateProfile;
import com.shivamtaneja.devconnect.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "Profile Controller")
@Slf4j
public class ProfileController {
  private final ProfileService profileService;

  /**
   * Create a new developer profile.
   * - Validates the request body.
   * - Returns the created profile in the response.
   */
  @PostMapping()
  public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(@RequestBody @Valid CreateProfile profileDTO) {
    ProfileResponse newProfile = profileService.createProfile(profileDTO);

    ApiResponse<ProfileResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), newProfile, null, true);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Update an existing developer profile.
   * - Validates the request body.
   * - Returns the updated profile in the response.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@PathVariable("id") String profileID, @RequestBody @Valid UpdateProfile profileDTO) {
    ProfileResponse updatedProfile = profileService.updateProfile(profileID, profileDTO);

    ApiResponse<ProfileResponse> response = new ApiResponse<>(HttpStatus.OK.value(), updatedProfile, null, true);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Update an existing developer profile picture (SYNC).
   * - Accepts a multipart image file.
   * - Validates the request body.
   * - Returns the updated profile in the response.
   */
  @PatchMapping("/{id}/image/direct")
  public ResponseEntity<ApiResponse<ProfileResponse>> updateProfileImageSYNC(@PathVariable("id") String profileID, @RequestPart("image") MultipartFile image) throws IOException {
    ProfileResponse updatedProfile = profileService.updateProfileImageSYNC(profileID, image);

    ApiResponse<ProfileResponse> response = new ApiResponse<>(HttpStatus.OK.value(), updatedProfile, null, true);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Endpoint to generate a SAS (Shared Access Signature) URL for uploading a profile image.
   *
   * @param profileID The ID of the profile for which the image will be uploaded.
   * @return ApiResponse containing the SAS URL for direct upload.
   */
  @PostMapping("/{id}/image/upload-url")
  public ResponseEntity<ApiResponse<String>> updateProfileImageASYNC(@PathVariable("id") String profileID) {
    String sasUrl = profileService.generateProfileImageUploadUrl(profileID);

    ApiResponse<String> response = new ApiResponse<>(HttpStatus.ACCEPTED.value(), sasUrl, null, true);

    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
  }

  /**
   * Endpoint to finalize the profile image upload process.
   *
   * @param profileID The ID of the profile for which the image will be uploaded.
   * @return ApiResponse indicating success.
   */
  @PostMapping("/{id}/image/upload-complete")
  public ResponseEntity<ApiResponse<Void>> completeProfileImageUpload(@PathVariable("id") String profileID) {
    profileService.updateProfileImageUrl(profileID);

    ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), null, null, true);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Get a developer profile by ID.
   * - Returns the profile in the response.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable("id") String profileID) {
    ProfileResponse updatedProfile = profileService.getProfile(profileID);

    ApiResponse<ProfileResponse> response = new ApiResponse<>(HttpStatus.OK.value(), updatedProfile, null, true);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Delete a developer profile by ID.
   * - Returns HTTP 204 No Content on success.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteProfile(@PathVariable("id") String profileID) {
    profileService.deleteProfile(profileID);

    ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), null, null, true);

    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }
}