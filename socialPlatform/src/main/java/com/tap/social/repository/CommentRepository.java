package com.tap.social.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tap.social.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> findByPostId(Integer postId); // Get all comments for a specific post

	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
	Page<Comment> getCommentsForPost(Integer postId, Pageable pageable);

}
