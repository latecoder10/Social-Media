package com.tap.social.mapper;

import com.tap.social.dto.CommentDTO;
import com.tap.social.dto.CommentLikeDTO;
import com.tap.social.models.Comment;
import com.tap.social.models.CommentLike;
import com.tap.social.models.Post;
import com.tap.social.models.User;

public class CommentMapper {

    // Convert Comment to CommentDTO
    public static CommentDTO toDTO(Comment comment) {
        if (comment == null) return null;
        return new CommentDTO(comment.getId(), comment.getContent(), comment.getUser().getId(), comment.getPost().getId());
    }

    // Convert CommentDTO to Comment
    public static Comment toEntity(CommentDTO commentDTO, User user, Post post) {
        if (commentDTO == null) return null;
        Comment comment = new Comment();
        comment.setId(commentDTO.id());
        comment.setContent(commentDTO.content());
        comment.setUser(user);
        comment.setPost(post);
        return comment;
    }

    // Convert CommentLike to CommentLikeDTO
    public static CommentLikeDTO toDTO(CommentLike commentLike) {
        if (commentLike == null) return null;
        return new CommentLikeDTO(commentLike.getId(), commentLike.getUser().getId(), commentLike.getComment().getId(),commentLike.getCreatedAt());
    }

    // Convert CommentLikeDTO to CommentLike
    public static CommentLike toEntity(CommentLikeDTO commentLikeDTO, User user, Comment comment) {
        if (commentLikeDTO == null) return null;
        CommentLike commentLike = new CommentLike();
        commentLike.setId(commentLikeDTO.id());
        commentLike.setUser(user);
        commentLike.setComment(comment);
        commentLike.setCreatedAt(commentLikeDTO.createdAt());
        return commentLike;
    }
}
