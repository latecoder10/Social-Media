package com.tap.social.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ChatRecordDTO(
        Integer id,
        String chatName,
        String chatImage,
        List<Integer> participantIds, // List of user IDs for participants
        List<MessageDTO> messages, // List of messages in this chat
        LocalDateTime createdAt
) {}
