package com.tap.social.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.dto.PostDTO;
import com.tap.social.mapper.PostMapper;
import com.tap.social.models.Post;
import com.tap.social.models.User;
import com.tap.social.service.PostService;
import com.tap.social.service.UserService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestHeader("Authorization") String jwt,@RequestBody PostDTO postDTO) {
    	User reqUser=userService.findUserByJwt(jwt);
        Post newPost = postService.createNewPost(postDTO, reqUser.getId());
        return new ResponseEntity<>(PostMapper.convertToDTO(newPost), HttpStatus.CREATED); // Return DTO
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 NO CONTENT if no posts
        }
        List<PostDTO> postDTOs = posts.stream()
                                       .map(PostMapper::convertToDTO)
                                       .collect(Collectors.toList());
        return new ResponseEntity<>(postDTOs, HttpStatus.OK); // Return 200 OK
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Integer userId) {
        List<Post> posts = postService.findPostByUserId(userId);
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 NO CONTENT if no posts found for user
        }
        List<PostDTO> postDTOs = posts.stream()
                                       .map(PostMapper::convertToDTO)
                                       .collect(Collectors.toList());
        return new ResponseEntity<>(postDTOs, HttpStatus.OK); // Return 200 OK
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(PostMapper.convertToDTO(post), HttpStatus.OK); // Return DTO
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestHeader("Authorization") String jwt, @PathVariable Integer postId,
                                              @RequestBody PostDTO postDTO) {
        Post updatedPost = postService.updatePost(postId, postDTO, userService.findUserByJwt(jwt).getId());
        return new ResponseEntity<>(PostMapper.convertToDTO(updatedPost), HttpStatus.OK); // Return DTO
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId) {
        String responseMessage = postService.deletePost(postId, userService.findUserByJwt(jwt).getId());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK); // Return 200 OK
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<PostDTO> toggleLikePost(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId) {
    	User reqUser=userService.findUserByJwt(jwt);
        Post updatedPost = postService.toggleLikePost(postId, reqUser.getId());
        return new ResponseEntity<>(PostMapper.convertToDTO(updatedPost), HttpStatus.OK); // Return DTO
    }

    @PostMapping("/save/{postId}")
    public ResponseEntity<PostDTO> savePost(@RequestHeader("Authorization") String jwt, @PathVariable Integer postId) {
        Post savedPost = postService.savePost(postId, userService.findUserByJwt(jwt).getId());
        return new ResponseEntity<>(PostMapper.convertToDTO(savedPost), HttpStatus.OK); // Return DTO
    }

   


    // Additional endpoints can be added as needed
}
