package com.tap.social.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.dto.CommentLikeDTO;
import com.tap.social.mapper.CommentMapper;
import com.tap.social.models.CommentLike;
import com.tap.social.models.User;
import com.tap.social.service.CommentLikeService;
import com.tap.social.service.UserService;

@RestController
@RequestMapping("/api/comment-likes")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private UserService userService;

    @PostMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> addLike(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer commentId) {

        User user = userService.findUserByJwt(jwt);
        CommentLike createdLike = commentLikeService.toggleLikeComment(user.getId(), commentId);
        
        // Prepare response
        CommentLikeDTO createdLikeDTO = CommentMapper.toDTO(createdLike);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", createdLike != null ? "Like added" : "Like removed");
        response.put("like", createdLikeDTO);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<CommentLikeDTO>> getLikesForComment(@PathVariable Integer commentId) {
        List<CommentLike> likes = commentLikeService.getLikesForComment(commentId);

        List<CommentLikeDTO> likeDTOs = likes.stream()
                                             .map(CommentMapper::toDTO) // Static method reference
                                             .collect(Collectors.toList());

        return new ResponseEntity<>(likeDTOs, HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<Long> countLikesForComment(@PathVariable Integer commentId) {
        long count = commentLikeService.countLikesForComment(commentId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }


    @GetMapping("/user/{userId}/comment/{commentId}/exists")
    public ResponseEntity<Boolean> userHasLikedComment(@PathVariable Integer userId, @PathVariable Integer commentId) {
        boolean hasLiked = commentLikeService.userHasLikedComment(userId, commentId);
        return new ResponseEntity<>(hasLiked, HttpStatus.OK);
    }
}
