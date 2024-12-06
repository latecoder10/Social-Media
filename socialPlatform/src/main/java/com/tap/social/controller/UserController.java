package com.tap.social.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.dto.FollowerDTO;
import com.tap.social.dto.FollowingDTO;
import com.tap.social.dto.UserDTO; // Import UserDTO
import com.tap.social.mapper.UserMapper; // Import UserMapper
import com.tap.social.models.User;
import com.tap.social.response.PaginatedResponse;
import com.tap.social.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<User> users = userService.getAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 NO CONTENT if no users
		}
		List<UserDTO> userDTOs = users.stream().map(UserMapper::toDTO).collect(Collectors.toList()); // Convert list of
																										// Users to
																										// UserDTOs
		return new ResponseEntity<>(userDTOs, HttpStatus.OK); // Return 200 OK
	}

	@GetMapping("/paginated")
	public ResponseEntity<List<UserDTO>> getAllUsersPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<UserDTO> users = userService.getAllUsersPaginated(page, size);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/id/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer id) {
		User user = userService.findUserByUserId(id);
		UserDTO responseDTO = UserMapper.toDTO(user); // Convert Entity to DTO
		return new ResponseEntity<>(responseDTO, HttpStatus.OK); // Return 200 OK
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
		User user = userService.findUserByUserEmail(email);
		UserDTO responseDTO = UserMapper.toDTO(user); // Convert Entity to DTO
		return new ResponseEntity<>(responseDTO, HttpStatus.OK); // Return 200 OK
	}

	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUser(@RequestHeader("Authorization") String jwt,
			@RequestBody UserDTO userDTO) {
		User reqestedUser = userService.findUserByJwt(jwt);

		User userToUpdate = UserMapper.toEntity(userDTO); // Convert DTO to Entity
		User updatedUser = userService.updateUser(reqestedUser.getId(), userToUpdate); // Pass Entity to service
		UserDTO responseDTO = UserMapper.toDTO(updatedUser); // Convert Entity to DTO
		return new ResponseEntity<>(responseDTO, HttpStatus.OK); // Return 200 OK
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {
		userService.deleteUserById(userId);
		return new ResponseEntity<>("User deleted successfully with id " + userId, HttpStatus.OK); // Return 200 OK
	}

	@PostMapping("/follow/{followeeId}")
	public ResponseEntity<UserDTO> followUser(@RequestHeader("Authorization") String jwt,
			@PathVariable Integer followeeId) {
		User reqUser = userService.findUserByJwt(jwt);
		User followedUser = userService.followUser(reqUser.getId(), followeeId);
		UserDTO responseDTO = UserMapper.toDTO(followedUser); // Convert Entity to DTO
		return new ResponseEntity<>(responseDTO, HttpStatus.OK); // Return 200 OK
	}

	@PostMapping("/{followerId}/unfollow/{followeeId}")
	public ResponseEntity<UserDTO> unfollowUser(@PathVariable Integer followerId, @PathVariable Integer followeeId) {
		User unfollowedUser = userService.unfollowUser(followerId, followeeId);
		UserDTO responseDTO = UserMapper.toDTO(unfollowedUser); // Convert Entity to DTO
		return new ResponseEntity<>(responseDTO, HttpStatus.OK); // Return 200 OK
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam("query") String query) {
		List<User> users = userService.searchUser(query);
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 NO CONTENT if no matches
		}
		List<UserDTO> userDTOs = users.stream().map(UserMapper::toDTO).collect(Collectors.toList()); // Convert list of
																										// Users to
																										// UserDTOs
		return new ResponseEntity<>(userDTOs, HttpStatus.OK); // Return 200 OK
	}

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getUserFromToken(@RequestHeader("Authorization") String jwt) {
		System.out.println("jwt----------" + jwt);
		User user = userService.findUserByJwt(jwt);
		return new ResponseEntity<>(UserMapper.toDTO(user), HttpStatus.OK);
	}
	

    // Endpoint to get paginated followers
    @GetMapping("/{userId}/followers")
    public ResponseEntity<PaginatedResponse<FollowerDTO>> getPaginatedFollowers(
            @PathVariable Integer userId,
            @PageableDefault(size = 10) Pageable pageable) {
        
        PaginatedResponse<FollowerDTO> paginatedFollowers = userService.getPaginatedFollowers(userId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(paginatedFollowers);
    }

    // Endpoint to get paginated followings
    @GetMapping("/{userId}/followings")
    public ResponseEntity<PaginatedResponse<FollowingDTO>> getPaginatedFollowings(
            @PathVariable Integer userId,
            @PageableDefault(size = 10) Pageable pageable) {
        
        PaginatedResponse<FollowingDTO> paginatedFollowings = userService.getPaginatedFollowings(userId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(paginatedFollowings);
    }

	// Additional endpoints can be added as needed
}
