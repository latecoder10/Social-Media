package com.tap.social.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats") // Meaningful table name for the Chat entity
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_id") // Meaningful column name for the ID
    private Integer id;

    @Column(name = "chat_name") // Meaningful column name for the chat name
    private String chatName;
    
    @Column(name = "chat_image") // Meaningful column name for the chat image
    private String chatImage;

    @ManyToMany // Assuming multiple users can be part of a chat
    @JoinTable(
        name = "chat_participants", // Meaningful join table name
        joinColumns = @JoinColumn(name = "chat_id"), // Foreign key for the chat
        inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key for the user
    )
    private List<User> participants=new ArrayList<>(); // List of users in the chat

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL) 
    @Column(name = "messages") // Optional: Not typically needed for OneToMany; the messages will be in the Message entity
    private List<Message> messages=new ArrayList<>(); // List of messages in the chat

    @Column(name = "created_at") // Meaningful column name for the creation timestamp
    private LocalDateTime createdAt; // Timestamp of chat creation
}
