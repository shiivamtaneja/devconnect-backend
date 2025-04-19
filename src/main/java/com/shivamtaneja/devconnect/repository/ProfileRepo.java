package com.shivamtaneja.devconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivamtaneja.devconnect.entity.Profile;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, String> {
  Boolean existsByEmail(String email);
}
