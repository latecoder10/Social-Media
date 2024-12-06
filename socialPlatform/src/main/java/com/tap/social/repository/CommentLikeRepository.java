package com.tap.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tap.social.models.CommentLike;
import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {
    List<CommentLike> findByCommentId(Integer commentId); // Get all likes for a specific comment

    long countByCommentId(Integer commentId); // Count likes for a specific comment

    boolean existsByUserIdAndCommentId(Integer userId, Integer commentId); // Check if a user has liked a comment
    
    CommentLike findByUserIdAndCommentId(Integer userId,Integer commentId);
}
