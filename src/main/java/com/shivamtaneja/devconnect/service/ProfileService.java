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

  public ProfileResponse getProfile(String profileID) {
    Profile profile = profileRepo.findById(profileID)
        .orElseThrow(() -> new NotFoundException(StringConstants.PROFILE_NOT_FOUND_MESSAGE + profileID));

    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setProfile(profile);
    return profileResponse;
  }

  public ProfileResponse createProfile(CreateProfile profileDTO) {
    if (profileRepo.existsByEmail(profileDTO.getEmail())) {
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

  public void deleteProfile(String profileID) {
    profileRepo.findById(profileID)
        .orElseThrow(() -> new NotFoundException(StringConstants.PROFILE_NOT_FOUND_MESSAGE + profileID));

    profileRepo.deleteById(profileID);
  }
}
