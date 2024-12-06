package com.tap.social.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable = false)
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who created the comment

    // Optionally, a relationship to a Post
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // The post to which the comment belongs
    
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentLike> commentLikes = new HashSet<>(); // Likes for the comment
    
    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();
    
    // Constructors, Getters, Setters...
    public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(String content, User user, Post post, Set<CommentLike> commentLikes, LocalDateTime createdAt) {
		super();
		this.content = content;
		this.user = user;
		this.post = post;
		this.commentLikes = commentLikes;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Set<CommentLike> getCommentLikes() {
		return commentLikes;
	}

	public void setCommentLikes(Set<CommentLike> commentLikes) {
		this.commentLikes = commentLikes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
    
    
}

