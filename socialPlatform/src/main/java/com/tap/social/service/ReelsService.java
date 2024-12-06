package com.tap.social.service;

import java.util.List;

import com.tap.social.models.Reels;

public interface ReelsService {
	List<Reels> findAllReels();

	List<Reels> findReelsByUserId(Integer userId);

	Reels createReel(Reels reel, Integer userId);

	Reels updateReel(Integer reelId, Reels updatedReel, Integer userId);

	void deleteReel(Integer reelId, Integer userId);

	Reels findReelById(Integer reelId);
}
