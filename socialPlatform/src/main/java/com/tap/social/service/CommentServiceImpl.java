package com.tap.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tap.social.dto.CommentDTO;
import com.tap.social.mapper.CommentMapper;
import com.tap.social.models.Comment;
import com.tap.social.models.Post;
import com.tap.social.models.User;
import com.tap.social.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Override
	public CommentDTO addComment(String content, User user, Post postById) {
    	Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(postById);
        // Set other properties as required, e.g., user, post
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toDTO(savedComment);
	}
    
    @Override
    public Page<CommentDTO> getCommentsForPost(Integer postId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.getCommentsForPost(postId, pageable);
        return commentsPage.map(CommentMapper::toDTO);
    }

   

	

    // Other methods as needed
}
