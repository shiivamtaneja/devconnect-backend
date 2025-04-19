package com.shivamtaneja.devconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivamtaneja.devconnect.entity.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, String> {

}
