package com.shivamtaneja.devconnect.service;

import org.springframework.stereotype.Service;

import com.shivamtaneja.devconnect.dto.profile.CreateProfile;
import com.shivamtaneja.devconnect.dto.profile.ProfileResponse;
import com.shivamtaneja.devconnect.dto.profile.UpdateProfile;
import com.shivamtaneja.devconnect.entity.Profile;
import com.shivamtaneja.devconnect.exceptions.ExistsException;
import com.shivamtaneja.devconnect.exceptions.NotFoundException;
import com.shivamtaneja.devconnect.repository.ProfileRepo;
import com.shivamtaneja.devconnect.utils.StringConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepo profileRepo;

  /**
   * Retrieve a profile by its ID.
   * Throws NotFoundException if the profile does not exist.
   *
   * @param profileID The ID of the profile to retrieve.
   * @return ProfileResponse containing the profile data.
   */
  public ProfileResponse getProfile(String profileID) {
    Profile profile = profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(StringConstants.PROFILE_NOT_FOUND_MESSAGE + profileID));

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
            .orElseThrow(() -> new NotFoundException(StringConstants.PROFILE_NOT_FOUND_MESSAGE + profileID));

    existingProfile.setBio(profileDTO.getBio());
    existingProfile.setGithubUrl(profileDTO.getGithubUrl());
    existingProfile.setLinkedinUrl(profileDTO.getLinkedinUrl());

    profileRepo.save(existingProfile);

    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setProfile(existingProfile);
    return profileResponse;
  }

  /**
   * Update an existing profile.
   * Throws NotFoundException if the profile does not exist.
   *
   * @param profileID  The ID of the profile to update.
//   * @param profileDTO The updated profile data.
   * @return ProfileResponse containing the updated profile.
   */
  public ProfileResponse updateProfileImage(String profileID) {
    Profile existingProfile = profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(StringConstants.PROFILE_NOT_FOUND_MESSAGE + profileID));

    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setProfile(existingProfile);
    return profileResponse;
  }

  /**
   * Delete a profile by its ID.
   * Throws NotFoundException if the profile does not exist.
   *
   * @param profileID The ID of the profile to delete.
   */
  public void deleteProfile(String profileID) {
    profileRepo.findById(profileID)
            .orElseThrow(() -> new NotFoundException(StringConstants.PROFILE_NOT_FOUND_MESSAGE + profileID));

    profileRepo.deleteById(profileID);
  }
}
