package com.tap.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tap.social.exception.ReelNotFoundException;
import com.tap.social.exception.UnauthorizedAccessException;
import com.tap.social.models.Reels;
import com.tap.social.models.User;
import com.tap.social.repository.ReelsRepository;

import jakarta.transaction.Transactional;

@Service
public class ReelsServiceImpl implements ReelsService {

	@Autowired
	private ReelsRepository reelsRepository;

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public Reels createReel(Reels reel, Integer userId) {
		User user = userService.findUserByUserId(userId);

		reel.setUser(user);
		return reelsRepository.save(reel);
	}

	@Override
	public List<Reels> findAllReels() {
		return reelsRepository.findAll();
	}

	@Override
	public List<Reels> findReelsByUserId(Integer userId) {
		User user = userService.findUserByUserId(userId);
		return reelsRepository.findByUserId(user.getId());
	}

	@Transactional
	@Override
	public Reels updateReel(Integer reelId, Reels updatedReel, Integer userId) {
		Reels reel = reelsRepository.findById(reelId)
				.orElseThrow(() -> new ReelNotFoundException("Reel with ID " + reelId + " not found"));

		if (!reel.getUser().getId().equals(userId)) {
			throw new UnauthorizedAccessException("You are not authorized to update this reel.");
		}

		reel.setTitle(updatedReel.getTitle());
		reel.setVideo(updatedReel.getVideo());
		reel.setDescription(updatedReel.getDescription());
		reel.setDuration(updatedReel.getDuration());
		reel.setUpdatedAt(LocalDateTime.now());

		return reelsRepository.save(reel);
	}

	@Transactional
	@Override
	public void deleteReel(Integer reelId, Integer userId) {
		Reels reel = reelsRepository.findById(reelId)
				.orElseThrow(() -> new ReelNotFoundException("Reel with ID " + reelId + " not found"));

		if (!reel.getUser().getId().equals(userId)) {
			throw new UnauthorizedAccessException("You are not authorized to delete this reel.");
		}

		reelsRepository.delete(reel);
	}

	@Override
	public Reels findReelById(Integer reelId) {
		Reels reel = reelsRepository.findById(reelId)
				.orElseThrow(() -> new ReelNotFoundException("Reel with ID " + reelId + " not found"));
		return reel;
	}
}
