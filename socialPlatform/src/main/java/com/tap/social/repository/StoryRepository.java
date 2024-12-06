package com.tap.social.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.social.models.Story;

public interface StoryRepository extends JpaRepository<Story, Integer> {

	List<Story> findByUserId(Integer userId);
	// Example of a custom query using @Query
//    @Query("SELECT s FROM Story s WHERE s.user.id = :userId")
//    List<Story> findStoriesByUserId(@Param("userId") Integer userId);

	List<Story> findByUserIdAndExpirationTimeAfter(Integer userId, LocalDateTime now);

	List<Story> findByExpirationTimeBefore(LocalDateTime expirationTime); // For cleanup tasks
}
