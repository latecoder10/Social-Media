package com.tap.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tap.social.dto.PostDTO;
import com.tap.social.exception.PostNotFoundException;
import com.tap.social.exception.UserNotFoundException;
import com.tap.social.models.Post;
import com.tap.social.models.User;
import com.tap.social.repository.PostRepository;
import com.tap.social.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createNewPost(PostDTO postDTO, Integer userId) {
        // Validate that the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User found with id: " + userId));

        // Create a new Post object and set the necessary fields from DTO
        Post newPost = new Post();
        newPost.setCaption(postDTO.getCaption());
        newPost.setImage(postDTO.getImage());
        newPost.setVideo(postDTO.getVideo());
        newPost.setUser(user); // Associate the post with the user
        newPost.setCreatedAt(LocalDateTime.now()); // Set the created time to now

        // Save the post in the repository
        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) {
        // Fetch the post and validate that it exists
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No Post found with id: " + postId));

        // Check if the user is the owner of the post
        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to delete this post");
        }

        // Delete the post
        postRepository.delete(post);
        return "Post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        // Validate the user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User found with id: " + userId));

        // Fetch and return posts by userId
        return postRepository.findByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) {
        // Fetch the post by id and return it
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No Post found with id: " + postId));
    }

    @Override
    public List<Post> findAllPosts() {
        // Fetch and return all posts
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new PostNotFoundException("No posts found in the system");
        }
        return posts;
    }

    @Override
    public Post updatePost(Integer postId, PostDTO postDTO, Integer userId) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        // Check if the user is authorized to update the post
        if (!existingPost.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not authorized to update this post");
        }

        // Update fields of the existing post from DTO
        existingPost.setCaption(postDTO.getCaption());
        existingPost.setImage(postDTO.getImage());
        existingPost.setVideo(postDTO.getVideo());

        return postRepository.save(existingPost); // Save the updated post
    }

    @Override
    public Post savePost(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        user.getSavedPosts().add(post); // Assuming User has a savedPosts list
        userRepository.save(user); // Save the user with the updated saved posts

        return post;
    }

    @Override
    public Post toggleLikePost(Integer postId, Integer userId) {
        // Fetch the post by ID, throwing an exception if not found
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        // Fetch the user by ID, throwing an exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Check if the post is already liked by the user
        boolean isLiked = post.getLikes().stream().anyMatch(like -> like.getUser().equals(user));

        if (isLiked) {
            // If already liked, remove the like
            post.removeLike(user);
        } else {
            // If not liked, add the like
            post.addLike(user);
        }

        // Save the updated post and return it
        return postRepository.save(post);
    }

}
