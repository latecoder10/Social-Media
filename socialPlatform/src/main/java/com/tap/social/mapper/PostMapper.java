package com.tap.social.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.tap.social.dto.PostDTO;
import com.tap.social.dto.UserDTO;
import com.tap.social.models.Post;
import com.tap.social.models.User;

public class PostMapper {
	 // Convert Post to PostDTO
    public static PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setImage(post.getImage());
        postDTO.setVideo(post.getVideo());
        postDTO.setCreatedAt(post.getCreatedAt());

        // Populate user information from User object
        User user = post.getUser();
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getGender());
        postDTO.setUser(userDTO);

        // Populate likedBy information from likes
        Set<UserDTO> likedByUsers = post.getLikes().stream()
            .map(like -> new UserDTO(like.getUser().getId(), like.getUser().getFirstName(), like.getUser().getLastName(), like.getUser().getEmail(), like.getUser().getGender()))
            .collect(Collectors.toSet());
        postDTO.setLikedBy(likedByUsers);

        return postDTO;
    }
}
