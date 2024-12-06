package com.tap.social.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tap.social.models.Story;
import com.tap.social.repository.StoryRepository;

@Component
public class StoryCleanupTask {

	private static final Logger logger = LoggerFactory.getLogger(StoryCleanupTask.class);


	private StoryRepository storyRepository;

	@Autowired
	public StoryCleanupTask(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}

	@Scheduled(cron = "0 0 * * * ?") // Runs every minute for testing
	public void cleanupExpiredStories() {
		LocalDateTime now = LocalDateTime.now();
		List<Story> expiredStories = storyRepository.findByExpirationTimeBefore(now);

		if (!expiredStories.isEmpty()) {
			storyRepository.deleteAll(expiredStories);
			logger.info("Deleted {} expired stories at {}", expiredStories.size(), now);
		} else {
			logger.info("No expired stories to delete at {}", now);
		}
	}
}
