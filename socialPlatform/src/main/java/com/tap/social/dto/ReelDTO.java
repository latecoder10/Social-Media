package com.tap.social.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record ReelDTO(@Nullable Integer id,

		@NotNull(message = "Title cannot be null") @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters") String title,

		@NotNull(message = "Video cannot be null") String video,

		@Nullable String description,

		@Nullable Integer duration,

		@Nullable LocalDateTime createdAt,

		@Nullable LocalDateTime updatedAt) {
}
