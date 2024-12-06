package com.tap.social.service;

import java.util.List;

import com.tap.social.dto.PostDTO;
import com.tap.social.models.Post;

public interface PostService {
    Post createNewPost(PostDTO postDTO, Integer userId);
    
    String deletePost(Integer postId, Integer userId);
    
    List<Post> findPostByUserId(Integer userId);
    
    Post findPostById(Integer postId);
    
    List<Post> findAllPosts();
    
    Post updatePost(Integer postId, PostDTO postDTO, Integer userId);

    Post savePost(Integer postId, Integer userId);
    
    Post toggleLikePost(Integer postId, Integer userId);
}
