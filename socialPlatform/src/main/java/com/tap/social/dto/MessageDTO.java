package com.tap.social.dto;

import java.time.LocalDateTime;

public record MessageDTO(Integer id, String content, String image, Integer chatId, Integer senderId,

		LocalDateTime timestamp) {
}
