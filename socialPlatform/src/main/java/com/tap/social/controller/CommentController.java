package com.tap.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.dto.CommentDTO;
import com.tap.social.models.User;
import com.tap.social.service.CommentService;
import com.tap.social.service.PostService;
import com.tap.social.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(
            @RequestHeader("Authorization") String jwt, 
            @Valid @RequestBody CommentDTO commentDTO) {
        // If validation fails here, Spring will throw MethodArgumentNotValidException
        User user = userService.findUserByJwt(jwt);
        CommentDTO createdComment = commentService.addComment(commentDTO.content(), user, postService.findPostById(commentDTO.postId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPost(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CommentDTO> commentsPage = commentService.getCommentsForPost(postId, pageable);

        List<CommentDTO> comments = commentsPage.getContent();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
