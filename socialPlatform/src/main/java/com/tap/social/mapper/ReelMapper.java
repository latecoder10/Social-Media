package com.tap.social.mapper;

import com.tap.social.dto.ReelDTO;
import com.tap.social.models.Reels;
import org.springframework.stereotype.Component;

@Component
public class ReelMapper {

	public Reels toEntity(ReelDTO dto) {
		Reels reel = new Reels();
		reel.setId(dto.id()); // Nullable field
		reel.setTitle(dto.title());
		reel.setVideo(dto.video());
		reel.setDescription(dto.description());
		reel.setDuration(dto.duration());
		return reel;
	}

	public ReelDTO toDTO(Reels reel) {
		return new ReelDTO(reel.getId(), reel.getTitle(), reel.getVideo(), reel.getDescription(), reel.getDuration(),
				reel.getCreatedAt(), reel.getUpdatedAt());
	}
}
