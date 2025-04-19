package com.shivamtaneja.devconnect.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"projects", "posts"})
@Builder
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;
  private String bio;
  private String githubUrl;
  private String linkedinUrl;
  private String profileImageUrl;

  @Column(nullable = false, unique = true)
  private String email;

  private String password;
  
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Project> projects = new ArrayList<>();

  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Post> posts = new ArrayList<>();
}