package com.tap.social.mapper;

import com.tap.social.dto.StoryDTO;
import com.tap.social.models.Story;
import org.springframework.stereotype.Component;

@Component
public class StoryMapper {

	public StoryDTO toDTO(Story story) {
		if (story == null) {
			return null;
		}
		return new StoryDTO(story.getId(), story.getUser().getId(), // Assuming User entity has a getId() method
				story.getImage(), story.getCaptions(), story.getCreatedAt(), story.getUpdatedAt(), story.getDuration(),
				story.isActive(), story.getViewCount());
	}

	public Story toEntity(StoryDTO storyDTO) {
		if (storyDTO == null) {
			return null;
		}
		Story story = new Story();
		story.setId(storyDTO.id());
		story.setImage(storyDTO.image());
		story.setCaptions(storyDTO.captions());
		story.setDuration(storyDTO.duration());
		story.setActive(storyDTO.isActive());
		story.setViewCount(storyDTO.viewCount());
		return story;
	}
}
