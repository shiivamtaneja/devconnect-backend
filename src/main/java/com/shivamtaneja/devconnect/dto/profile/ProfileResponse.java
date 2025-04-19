package com.shivamtaneja.devconnect.dto.profile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shivamtaneja.devconnect.entity.Post;
import com.shivamtaneja.devconnect.entity.Profile;
import com.shivamtaneja.devconnect.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {
  private String id;
  private String name;
  private String bio;
  private String githubUrl;
  private String linkedinUrl;
  private String profileImageUrl;
  private String email;
  private List<Project> projects;
  private List<Post> post;

  public void setProfile(Profile profile) {
    this.id = profile.getId();
    this.name = profile.getName();
    this.bio = profile.getBio();
    this.githubUrl = profile.getGithubUrl();
    this.linkedinUrl = profile.getLinkedinUrl();
    this.profileImageUrl = profile.getProfileImageUrl();
    this.email = profile.getEmail();
    this.projects = profile.getProjects();
    this.post = profile.getPosts();
  }
}
