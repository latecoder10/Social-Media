package com.tap.social.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tap.social.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByFirstNameContainingOrLastNameContainingOrEmailContaining(String firstName, String lastName, String email);
    
    // Method for paginated search
    Page<User> findByFirstNameContainingOrLastNameContainingOrEmailContaining(String firstName, String lastName, String email, Pageable pageable);

    // Method to find followers of a user
    @Query("SELECT f FROM User u JOIN u.followers f WHERE u.id = :userId")
    Page<User> findFollowersByUserId(Integer userId, Pageable pageable);
    
    // Method to find followings of a user
    @Query("SELECT f FROM User u JOIN u.followings f WHERE u.id = :userId")
    Page<User> findFollowingsByUserId(Integer userId, Pageable pageable);
}
