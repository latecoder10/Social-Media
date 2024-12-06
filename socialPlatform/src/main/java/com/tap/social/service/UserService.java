package com.tap.social.service;

import java.util.List;

import com.tap.social.dto.FollowerDTO;
import com.tap.social.dto.FollowingDTO;
import com.tap.social.dto.UserDTO;
import com.tap.social.models.User;
import com.tap.social.response.PaginatedResponse;

public interface UserService {
    public User registerUser(User user);
    
    public User findUserByUserId(Integer userId); 

    public User findUserByUserEmail(String email); 
    
    public User followUser(Integer userId1, Integer userId2);
    
    public User updateUser(Integer userId, User user);
    
    public List<User> searchUser(String query);

	User unfollowUser(Integer followerId, Integer followeeId);

	public void deleteUserById(Integer userId);

	public List<User> getAllUsers(); 
	
	public User findUserByJwt(String jwt);

	List<UserDTO> getAllUsersPaginated(int page, int size);

	public PaginatedResponse<FollowerDTO> getPaginatedFollowers(Integer userId, int pageNumber, int pageSize);

	public PaginatedResponse<FollowingDTO> getPaginatedFollowings(Integer userId, int pageNumber, int pageSize);
}
