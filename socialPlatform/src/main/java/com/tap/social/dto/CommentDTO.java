package com.tap.social.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentDTO(Integer id, @NotBlank(message = "Content cannot be blank") String content, Integer userId, @NotNull(message = "Post ID cannot be null") Integer postId) {}
