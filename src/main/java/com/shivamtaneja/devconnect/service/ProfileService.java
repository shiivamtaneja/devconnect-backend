package com.shivamtaneja.devconnect.service;

import com.shivamtaneja.devconnect.utils.CustomExceptionMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shivamtaneja.devconnect.dto.profile.CreateProfile;
import com.shivamtaneja.devconnect.dto.profile.ProfileResponse;
import com.shivamtaneja.devconnect.dto.profile.UpdateProfile;
import com.shivamtaneja.devconnect.entity.Profile;
import com.shivamtaneja.devconnect.exceptions.ExistsException;
import com.shivamtaneja.devconnect.exceptions.NotFoundException;
import com.shivamtaneja.devconnect.repository.ProfileRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
  private final ProfileRepo profileRepo;
  private final AzureBlobService azureBlobService;

  @Value("${AZURE_BLOB_STORAGE_PROFILE_PICTURE_CONTAINER_NAME}")
  private String profileImagesContainer;

  @Value("${AZURE_BLOB_STORAGE_ACCOUNT_NAME}")
  private String storageAccountName;

  /**
   * Retrieve a profile by its ID.
   * Throws NotFoundException if the profile does not exist.
   *
   * @param profileID The ID of the profile to retrieve.
   * @return ProfileResponse containing the profile data.
   */
  public ProfileResponse getProfile(String profileID) {
    Profile profile = profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(
                    String.format(CustomExceptionMessages.PROFILE_NOT_FOUND, profileID)
            ));

    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setProfile(profile);
    return profileResponse;
  }

  /**
   * Create a new profile.
   * Checks for existing profile by email to prevent duplicates.
   *
   * @param profileDTO The data for the new profile.
   * @return ProfileResponse containing the created profile.
   */
  public ProfileResponse createProfile(CreateProfile profileDTO) {
    boolean profileExists = profileRepo.existsByEmail(profileDTO.getEmail());

    if (profileExists) {
      throw new ExistsException("Profile already exists");
    }

    Profile profile = new Profile();

    profile.setName(profileDTO.getName());
    profile.setBio(profileDTO.getBio());
    profile.setGithubUrl(profileDTO.getGithubUrl());
    profile.setLinkedinUrl(profileDTO.getLinkedinUrl());
    profile.setEmail(profileDTO.getEmail());

    // TODO: hash the pswd, and update profile to handle sso from different clients
    profile.setPassword(profileDTO.getPassword());

    Profile newProfile = profileRepo.save(profile);

    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setProfile(newProfile);
    return profileResponse;
  }

  /**
   * Update an existing profile.
   * Throws NotFoundException if the profile does not exist.
   *
   * @param profileID  The ID of the profile to update.
   * @param profileDTO The updated profile data.
   * @return ProfileResponse containing the updated profile.
   */
  public ProfileResponse updateProfile(String profileID, UpdateProfile profileDTO) {
    Profile existingProfile = profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(
                    String.format(CustomExceptionMessages.PROFILE_NOT_FOUND, profileID)
            ));

    existingProfile.setBio(profileDTO.getBio());
    existingProfile.setGithubUrl(profileDTO.getGithubUrl());
    existingProfile.setLinkedinUrl(profileDTO.getLinkedinUrl());

    profileRepo.save(existingProfile);

    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setProfile(existingProfile);
    return profileResponse;
  }

  /**
   * Update an existing profile's pic (Sync method).
   * Throws NotFoundException if the profile does not exist.
   *
   * @param profileID The ID of the profile to update.
   * @param image     The image to be set as profile picture.
   * @return ProfileResponse containing the updated profile.
   */
  public ProfileResponse updateProfileImageSYNC(String profileID, MultipartFile image) throws IOException {
    Profile existingProfile = profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(
                    String.format(CustomExceptionMessages.PROFILE_NOT_FOUND, profileID)
            ));

    String imageUrl = azureBlobService.uploadFile(image, profileImagesContainer, existingProfile.getId());
    existingProfile.setProfileImageUrl(imageUrl);
    profileRepo.save(existingProfile);

    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setProfile(existingProfile);
    return profileResponse;
  }

  /**
   * Generates a SAS URL for uploading a profile image for the given profile ID.
   *
   * @param profileID The ID of the profile to update.
   * @throws NotFoundException if the profile does not exist.
   */
  public String generateProfileImageUploadUrl(String profileID) {
    profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(
                    String.format(CustomExceptionMessages.PROFILE_NOT_FOUND, profileID)
            ));

    return azureBlobService.generateBlobSasUrl(profileImagesContainer, profileID);
  }

  /**
   * Updates the profileImageUrl for the given profile ID after verifying the image exists in Blob Storage.
   *
   * @param profileID The ID of the profile to update.
   * @throws NotFoundException if the profile or image does not exist.
   */
  public void updateProfileImageUrl(String profileID) {
    Profile profile = profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(
                    String.format(CustomExceptionMessages.PROFILE_NOT_FOUND, profileID)
            ));

    boolean fileExists = azureBlobService.fileExists(profileImagesContainer, profile.getId());

    if (!fileExists) {
      throw new NotFoundException("Profile picture not found! Can't save to db!");
    }

    String imageUrl = String.format("https://%s.blob.core.windows.net/%s/%s",
            storageAccountName, profileImagesContainer, profileID);

    profile.setProfileImageUrl(imageUrl);
    profileRepo.save(profile);
  }

  /**
   * Delete a profile by its ID.
   * Throws NotFoundException if the profile does not exist.
   *
   * @param profileID The ID of the profile to delete.
   */
  public void deleteProfile(String profileID) {
    profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(
                    String.format(CustomExceptionMessages.PROFILE_NOT_FOUND, profileID)
            ));

    profileRepo.deleteById(profileID);
  }
}
