package com.tap.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.social.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	 // Method to find posts by user ID
    List<Post> findByUserId(Integer userId);
}
