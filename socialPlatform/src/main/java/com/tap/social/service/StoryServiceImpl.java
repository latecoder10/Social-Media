package com.tap.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tap.social.exception.ReelNotFoundException;
import com.tap.social.exception.StoryNotFoundException;
import com.tap.social.exception.UnauthorizedAccessException;
import com.tap.social.models.Story;
import com.tap.social.models.User;
import com.tap.social.repository.StoryRepository;

import jakarta.transaction.Transactional;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public Story createStory(Story story, Integer userId) {
		User user = userService.findUserByUserId(userId);
		story.setUser(user); // Set the user for the story

		return storyRepository.save(story);
	}

	@Override
	public List<Story> findAllStories() {
		return storyRepository.findAll();
	}

	@Override
	public List<Story> findValidStories() {
		return storyRepository.findAll().stream()
				.filter(story -> story.getExpirationTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
	}

	@Override
	public Story findStoryById(Integer storyId) {
		return storyRepository.findById(storyId)
				.orElseThrow(() -> new StoryNotFoundException("Story with ID " + storyId + " not found"));
	}

	@Override
	public List<Story> findStoriesByUserId(Integer userId) {
		User user = userService.findUserByUserId(userId);
		return storyRepository.findByUserId(user.getId());
	}

	@Transactional
	@Override
	public Story updateStory(Integer storyId, Story updatedStory, Integer userId) {
		Story story = findStoryById(storyId);
		// Optional: Check if the story belongs to the user if necessary
		story.setImage(updatedStory.getImage());
		story.setCaptions(updatedStory.getCaptions());
		story.setDuration(updatedStory.getDuration());
		story.setActive(updatedStory.isActive());
		return storyRepository.save(story);
	}

	@Override
	public void deleteStory(Integer storyId, Integer userId) {
		Story story = findStoryById(storyId);
		
		// Check if the story belongs to the user if necessary
		if(!story.getUser().getId().equals(userId)) {
			throw new UnauthorizedAccessException("You are not authorized to delete this story.");
		}
		storyRepository.delete(story);
	}
}
