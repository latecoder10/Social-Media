package com.tap.social.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content; // Message content

    private String image;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat; // Reference to the chat
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender; // Reference to the user who sent the message

    private LocalDateTime sentAt; // Timestamp for when the message was sent
}
