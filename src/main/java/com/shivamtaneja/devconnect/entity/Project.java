package com.shivamtaneja.devconnect.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "projects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "profile")
@Builder
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String title;
  private String description;
  private String techStack;
  private String githubRepo;
  private String liveDemoUrl;

  private LocalDateTime createdAt;

  @PrePersist
  public void setCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

  @ManyToOne
  @JoinColumn(name = "profile_id")
  private Profile profile;
}
