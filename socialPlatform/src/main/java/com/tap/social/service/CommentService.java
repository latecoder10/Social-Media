package com.tap.social.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tap.social.dto.CommentDTO;
import com.tap.social.models.Post;
import com.tap.social.models.User;

public interface CommentService  {
	CommentDTO addComment(String content, User user, Post postById);

	Page<CommentDTO> getCommentsForPost(Integer postId, Pageable pageable);

}
