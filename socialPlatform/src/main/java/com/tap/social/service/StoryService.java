package com.tap.social.service;

import com.tap.social.models.Story;

import java.util.List;

public interface StoryService {
    Story createStory(Story story, Integer userId);
    List<Story> findAllStories();
    Story findStoryById(Integer storyId);
    List<Story> findStoriesByUserId(Integer userId);
    Story updateStory(Integer storyId, Story updatedStory, Integer userId); // Include userId
    void deleteStory(Integer storyId, Integer userId); // Include userId
	List<Story> findValidStories();
}
