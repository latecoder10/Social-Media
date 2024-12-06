package com.tap.social.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Story {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-incrementing
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY) // Many stories can belong to one user
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false) // Ensure image field is not null
	private String image;

	private String captions; // Optional captions for the story

	@Column(nullable = false, updatable = false) // Ensure createdAt is not null and cannot be updated
	private LocalDateTime createdAt;

	@Column(nullable = false) // Ensure updatedAt is not null
	private LocalDateTime updatedAt;
	
	@Column(nullable = false) 
	private LocalDateTime expirationTime;
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
		expirationTime=createdAt.plusMinutes(1);// Set expiration time to 24 hours later
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	// Additional fields you might consider adding:
	private Integer duration; // Duration the story is available (in seconds)

	@Column(nullable = false) // Indicates if the story is active
	private boolean isActive = true;

	// Optional: You could include fields for tracking views or interactions, such

	private Integer viewCount = 0; // Count of how many times the story has been viewed
	
}
