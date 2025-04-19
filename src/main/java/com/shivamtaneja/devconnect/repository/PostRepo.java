package com.shivamtaneja.devconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivamtaneja.devconnect.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, String> {

}
