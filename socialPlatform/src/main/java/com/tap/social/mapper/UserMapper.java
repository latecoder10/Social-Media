package com.tap.social.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.tap.social.dto.FollowerDTO;
import com.tap.social.dto.FollowingDTO;
import com.tap.social.dto.UserDTO;
import com.tap.social.models.User;

public class UserMapper {

    // Convert User to UserDTO
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender());
        userDTO.setPassword(user.getPassword()); // Ensure this is handled securely
        
        // Convert followers to FollowerDTO
        Set<FollowerDTO> followerDTOs = user.getFollowers().stream()
            .map(follower -> new FollowerDTO(follower.getId(), follower.getFirstName(), follower.getLastName(), follower.getEmail(), follower.getGender()))
            .collect(Collectors.toSet());
        userDTO.setFollowers(followerDTOs);

        // Convert followings to FollowingDTO
        Set<FollowingDTO> followingDTOs = user.getFollowings().stream()
            .map(following -> new FollowingDTO(following.getId(), following.getFirstName(), following.getLastName(), following.getEmail(), following.getGender()))
            .collect(Collectors.toSet());
        userDTO.setFollowings(followingDTOs);

        // Add other fields as necessary
        return userDTO;
    }

    // Convert UserDTO to User
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setPassword(userDTO.getPassword()); // Make sure to handle password securely
        
        // Handle followers and followings if needed 
        // Set<Follower> followers = new HashSet<>(); // If Follower model exists
        // user.setFollowers(followers);
        
        // Set<Following> followings = new HashSet<>(); // If Following model exists
        // user.setFollowings(followings);

        // Add other fields as necessary
        return user;
    }
}
