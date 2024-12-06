package com.tap.social.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.dto.StoryDTO;
import com.tap.social.mapper.StoryMapper;
import com.tap.social.models.Story;
import com.tap.social.service.StoryService;
import com.tap.social.service.UserService; // Import UserService for user retrieval

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;
    private final StoryMapper storyMapper;
    private final UserService userService; // To fetch user details

    public StoryController(StoryService storyService, StoryMapper storyMapper, UserService userService) {
        this.storyService = storyService;
        this.storyMapper = storyMapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@Valid @RequestBody StoryDTO storyDTO, 
                                                 @RequestHeader("Authorization") String jwt) {
        Integer userId = userService.findUserByJwt(jwt).getId(); // Extract userId from JWT
        Story createdStory = storyService.createStory(storyMapper.toEntity(storyDTO), userId);
        return ResponseEntity.ok(storyMapper.toDTO(createdStory));
    }

    @GetMapping
    public ResponseEntity<List<StoryDTO>> findAllStories() {
        List<Story> stories = storyService.findAllStories();
        return ResponseEntity.ok(stories.stream()
                .map(storyMapper::toDTO)
                .toList());
    }
    
    @GetMapping("/valid")
    public ResponseEntity<List<StoryDTO>> findValidStories() {
        List<Story> validStories = storyService.findValidStories();
        return ResponseEntity.ok(validStories.stream()
                .map(storyMapper::toDTO)
                .collect(Collectors.toList()));
    }


    @GetMapping("/{storyId}")
    public ResponseEntity<StoryDTO> findStoryById(@PathVariable Integer storyId) {
        Story story = storyService.findStoryById(storyId);
        return ResponseEntity.ok(storyMapper.toDTO(story));
    }

    @GetMapping("/user")
    public ResponseEntity<List<StoryDTO>> findStoriesByUserId(@RequestHeader("Authorization") String jwt) {
        Integer userId = userService.findUserByJwt(jwt).getId(); // Extract userId from JWT
        List<Story> stories = storyService.findStoriesByUserId(userId);
        return ResponseEntity.ok(stories.stream()
                .map(storyMapper::toDTO)
                .toList());
    }

    @PutMapping("/{storyId}")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable Integer storyId, 
                                                 @Valid @RequestBody StoryDTO storyDTO, 
                                                 @RequestHeader("Authorization") String jwt) {
        Integer userId = userService.findUserByJwt(jwt).getId(); // Extract userId from JWT
        Story updatedStory = storyService.updateStory(storyId, storyMapper.toEntity(storyDTO), userId);
        return ResponseEntity.ok(storyMapper.toDTO(updatedStory));
    }
    

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable Integer storyId, 
                                            @RequestHeader("Authorization") String jwt) {
        Integer userId = userService.findUserByJwt(jwt).getId(); // Extract userId from JWT
        storyService.deleteStory(storyId, userId);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
}
