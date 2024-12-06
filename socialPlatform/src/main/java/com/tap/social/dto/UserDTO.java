package com.tap.social.dto;

import java.util.HashSet;
import java.util.Set;

import com.tap.social.models.PostLike;

public class UserDTO {
	private Integer id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String gender;

	// Followers and Followings as UserDTO
	private Set<FollowerDTO> followers = new HashSet<>();
	private Set<FollowingDTO> followings = new HashSet<>();

	// Saved Posts as PostDTO
	private Set<PostDTO> savedPosts = new HashSet<>();

	private Set<PostLike> likedPosts = new HashSet<>();

	// Constructors, Getters, and Setters
	public UserDTO() {
		// Default constructor
	}

	public UserDTO(Integer id, String firstName, String lastName, String email, String gender) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Set<FollowerDTO> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<FollowerDTO> followers) {
		this.followers = followers;
	}

	public Set<FollowingDTO> getFollowings() {
		return followings;
	}

	public void setFollowings(Set<FollowingDTO> followings) {
		this.followings = followings;
	}

	public Set<PostDTO> getSavedPosts() {
		return savedPosts;
	}

	public Set<PostLike> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(Set<PostLike> likedPosts) {
		this.likedPosts = likedPosts;
	}

	public void setSavedPosts(Set<PostDTO> savedPosts) {
		this.savedPosts = savedPosts;
	}

	public String getPassword() { // Add this method
		return password;
	}

	public void setPassword(String password) { // Add this method
		this.password = password;
	}
}
