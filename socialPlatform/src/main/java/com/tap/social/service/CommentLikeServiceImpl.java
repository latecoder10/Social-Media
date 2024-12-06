package com.tap.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tap.social.exception.PostNotFoundException;
import com.tap.social.exception.UserNotFoundException;
import com.tap.social.models.Comment;
import com.tap.social.models.CommentLike;
import com.tap.social.models.Post;
import com.tap.social.models.User;
import com.tap.social.repository.CommentLikeRepository;
import com.tap.social.repository.CommentRepository;
import com.tap.social.repository.UserRepository;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    // Add a like to a comment
    @Override
    public CommentLike toggleLikeComment(Integer userId, Integer commentId) {
    	// Fetch the user by ID, throwing an exception if not found
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    	
    	Comment comment = commentRepository.findById(commentId)
    			.orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    	
    	CommentLike existingCommentLike=commentLikeRepository.findByUserIdAndCommentId(userId, commentId);
        
    	CommentLike savedLike=null;
    	if(existingCommentLike!=null) {
    		commentLikeRepository.delete(existingCommentLike);
    	}else {
    		CommentLike commentLike = new CommentLike();
    		commentLike.setUser(user);
    		commentLike.setComment(comment);
    		commentLike.setCreatedAt(LocalDateTime.now());
    		savedLike= commentLikeRepository.save(commentLike);
    		
    	}
    	
 
        return savedLike;
    }
    
    // Get all likes for a specific comment
    @Override
    public List<CommentLike> getLikesForComment(Integer commentId) {
        return commentLikeRepository.findByCommentId(commentId);
    }

    // Count the number of likes for a specific comment
    @Override
    public long countLikesForComment(Integer commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }

    // Check if a specific user has liked a specific comment
    @Override
    public boolean userHasLikedComment(Integer userId, Integer commentId) {
        return commentLikeRepository.existsByUserIdAndCommentId(userId, commentId);
    }

}
