package com.tap.social.service;

import java.util.List;

import com.tap.social.models.CommentLike;

public interface CommentLikeService {
	public List<CommentLike> getLikesForComment(Integer commentId);

	public long countLikesForComment(Integer commentId);

	public boolean userHasLikedComment(Integer userId, Integer commentId);

	CommentLike toggleLikeComment(Integer userId, Integer commentId);




}
