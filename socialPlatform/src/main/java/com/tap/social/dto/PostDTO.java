package com.tap.social.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PostDTO {
    private Integer id;

    @NotNull(message = "Caption cannot be null")
    @Size(min = 1, max = 255, message = "Caption should be between 1 and 255 characters")
    private String caption;

    private String image;

    private String video;

    private LocalDateTime createdAt;

    // Count of likes


    // Users who liked the post
    private Set<UserDTO> likedBy = new HashSet<>(); // Assuming you have a UserDTO class

    // User who created the post
    private UserDTO user; // Assuming you have a UserDTO class

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Set<UserDTO> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<UserDTO> likedBy) {
        this.likedBy = likedBy;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
