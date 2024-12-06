package com.tap.social.dto;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StoryDTO(
    @Nullable
    Integer id,
    
    @Nullable
    Integer userId, // To maintain user reference
    
    @NotNull(message = "Image cannot be null")
    String image,
    
    @Size(max = 255, message = "Captions cannot exceed 255 characters")
    String captions,
    
    @Nullable
    LocalDateTime createdAt,
    
    @Nullable
    LocalDateTime updatedAt,
    
   @Nullable
    Integer duration,
    
    boolean isActive, // This can be optional and can have a default value
    
    Integer viewCount
) {}
