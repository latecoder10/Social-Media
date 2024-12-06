package com.tap.social.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tap.social.configuration.JwtProvider;
import com.tap.social.dto.FollowerDTO;
import com.tap.social.dto.FollowingDTO;
import com.tap.social.dto.UserDTO;
import com.tap.social.exception.UserNotFoundException;
import com.tap.social.mapper.UserMapper;
import com.tap.social.models.Post;
import com.tap.social.models.User;
import com.tap.social.repository.UserRepository;
import com.tap.social.response.PaginatedResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        validateUserInput(user);
        checkEmailUniqueness(user.getEmail());

        return userRepository.save(user);
    }

    private void validateUserInput(User user) {
        Optional.ofNullable(user.getEmail()).filter(email -> !email.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Email must not be null or empty"));
        Optional.ofNullable(user.getFirstName()).filter(firstName -> !firstName.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("First name must not be null or empty"));
        Optional.ofNullable(user.getLastName()).filter(lastName -> !lastName.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Last name must not be null or empty"));
        Optional.ofNullable(user.getPassword()).filter(password -> !password.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Password must not be null or empty"));
    }

    private void checkEmailUniqueness(String email) {
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            throw new IllegalArgumentException("Email is already in use");
        });
    }

    @Override
    public User findUserByUserId(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No User found with id: " + userId));
    }

    @Override
    public User findUserByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + email));
    }

    @Override
    public User followUser(Integer followerId, Integer followeeId) {
        validateFollowOperation(followerId, followeeId);

        User follower = findUserByUserId(followerId);
        User followee = findUserByUserId(followeeId);

        follower.getFollowings().add(followee);
        followee.getFollowers().add(follower);
        userRepository.save(follower);
        userRepository.save(followee);

        return followee;
    }

    private void validateFollowOperation(Integer followerId, Integer followeeId) {
        if (followerId.equals(followeeId)) {
            throw new IllegalArgumentException("A user cannot follow themselves");
        }
    }

    @Override
    public User unfollowUser(Integer followerId, Integer followeeId) {
        validateFollowOperation(followerId, followeeId);

        User follower = findUserByUserId(followerId);
        User followee = findUserByUserId(followeeId);

        if (!follower.getFollowings().remove(followee)) {
            throw new IllegalArgumentException("You are not following this user");
        }

        followee.getFollowers().remove(follower);
        userRepository.save(follower);
        userRepository.save(followee);

        return followee;
    }

    @Override
    public User updateUser(Integer userId, User user) {
        User existingUser = findUserByUserId(userId);
        
        // Update fields if they have been provided
        updateUserFields(existingUser, user);

        return userRepository.save(existingUser);
    }

    private void updateUserFields(User existingUser, User user) {
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            checkEmailUniqueness(user.getEmail());
            existingUser.setEmail(user.getEmail());
        }
        Optional.ofNullable(user.getFirstName()).ifPresent(existingUser::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(existingUser::setLastName);
        Optional.ofNullable(user.getPassword()).ifPresent(existingUser::setPassword);
        Optional.ofNullable(user.getGender()).ifPresent(existingUser::setGender);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(query, query, query);
    }

    @Override
    public void deleteUserById(Integer userId) {
        User user = findUserByUserId(userId);
     // Remove user from liked posts
        for (Post post : user.getLikedPosts()) {
            post.removeLike(user); // Use the method in the Post class to remove the like
        }

        // Optionally, remove the user from followers and followings
        for (User follower : user.getFollowers()) {
            follower.getFollowings().remove(user);
        }

        for (User following : user.getFollowings()) {
            following.getFollowers().remove(user);
        }
        userRepository.delete(user);
        
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found in the system.");
        }
        return users;
    }
    
    @Override
    public List<UserDTO> getAllUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        
        return userPage.getContent().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    // Method to get paginated followers
    public PaginatedResponse<FollowerDTO> getPaginatedFollowers(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> followersPage = userRepository.findFollowersByUserId(userId, pageable);
        
        List<FollowerDTO> followersDTO = followersPage.getContent().stream()
            .map(user -> new FollowerDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getGender()))
            .toList();

        return new PaginatedResponse<>(followersDTO, followersPage.getNumber(), followersPage.getSize(), followersPage.getTotalElements(), followersPage.getTotalPages(), followersPage.isLast());
    }

    // Method to get paginated followings
    public PaginatedResponse<FollowingDTO> getPaginatedFollowings(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> followingsPage = userRepository.findFollowingsByUserId(userId, pageable);
        
        List<FollowingDTO> followingsDTO = followingsPage.getContent().stream()
            .map(user -> new FollowingDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getGender()))
            .toList();

        return new PaginatedResponse<>(followingsDTO, followingsPage.getNumber(), followingsPage.getSize(), followingsPage.getTotalElements(), followingsPage.getTotalPages(), followingsPage.isLast());
    }

	@Override
	public User findUserByJwt(String jwt) {
		 String email = JwtProvider.getEmailFromJwtToken(jwt); // Extract email from JWT
	        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found")); // Fetch user by email
	}
}
