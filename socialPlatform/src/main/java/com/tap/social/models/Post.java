package com.tap.social.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String caption;

    private String image;

    private String video;

    // Eager loading for user
    @ManyToOne(fetch = FetchType.EAGER) // Fetching user eagerly
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column in Post table
    private User user;

    private LocalDateTime createdAt;

    // One-to-many relationship for likes
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PostLike> likes = new HashSet<>();

    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    public Post() {
        // No-arg constructor
    }

    public Post(String caption, String image, String video, User user, LocalDateTime createdAt) {
        this.caption = caption;
        this.image = image;
        this.video = video;
        this.user = user;
        this.createdAt = createdAt;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Method to add a like
    public void addLike(User user) {
        PostLike like = new PostLike(user, this);
        likes.add(like);
    }

    // Method to remove a like
    public void removeLike(User user) {
        likes.removeIf(like -> like.getUser().equals(user));
    }

    public Set<PostLike> getLikes() {
        return likes;
    }

    public int getLikeCount() {
        return likes.size();
    }
}
