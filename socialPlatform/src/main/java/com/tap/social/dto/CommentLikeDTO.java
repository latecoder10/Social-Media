package com.tap.social.dto;

import java.time.LocalDateTime;

public record CommentLikeDTO(Integer id, Integer userId, Integer commentId,LocalDateTime createdAt) {}
