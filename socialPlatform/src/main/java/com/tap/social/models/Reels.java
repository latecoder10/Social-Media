package com.tap.social.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a default constructor
@AllArgsConstructor // Generates a constructor with all fields
public class Reels {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String title;
    private String video;
    private String description; // Description field
    private Integer duration; // Duration in seconds
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt; // Creation timestamp
    private LocalDateTime updatedAt; // Last update timestamp

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
