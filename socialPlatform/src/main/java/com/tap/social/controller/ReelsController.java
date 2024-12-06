package com.tap.social.controller;

import com.tap.social.dto.ReelDTO;
import com.tap.social.mapper.ReelMapper;
import com.tap.social.models.Reels;
import com.tap.social.models.User;
import com.tap.social.service.ReelsService;
import com.tap.social.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reels")
public class ReelsController {

    private final ReelsService reelsService;
    private final ReelMapper reelMapper;
    private final UserService userService;

    public ReelsController(ReelsService reelsService, ReelMapper reelMapper, UserService userService) {
        this.reelsService = reelsService;
        this.reelMapper = reelMapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ReelDTO> createReel(
            @RequestHeader("Authorization") String jwt,
            @Valid @RequestBody ReelDTO reelDTO) {
        
        User reqUser = userService.findUserByJwt(jwt);
        Reels createdReel = reelsService.createReel(reelMapper.toEntity(reelDTO), reqUser.getId());
        return ResponseEntity.ok(reelMapper.toDTO(createdReel));
    }

    @GetMapping
    public ResponseEntity<List<ReelDTO>> findAllReels() {
        List<Reels> reels = reelsService.findAllReels();
        return ResponseEntity.ok(reels.stream()
            .map(reelMapper::toDTO)
            .toList());
    }

    @GetMapping("/{reelId}")
    public ResponseEntity<ReelDTO> findReelById(@PathVariable Integer reelId) {
        Reels reel = reelsService.findReelById(reelId);
        return ResponseEntity.ok(reelMapper.toDTO(reel));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReelDTO>> findReelsByUserId(@PathVariable Integer userId) {
        List<Reels> reels = reelsService.findReelsByUserId(userId);
        return ResponseEntity.ok(reels.stream()
            .map(reelMapper::toDTO)
            .toList());
    }

    @PutMapping("/{reelId}")
    public ResponseEntity<ReelDTO> updateReel(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer reelId, 
            @Valid @RequestBody ReelDTO reelDTO) {
        
        User reqUser = userService.findUserByJwt(jwt);
        Reels updatedReel = reelsService.updateReel(reelId, reelMapper.toEntity(reelDTO), reqUser.getId());
        return ResponseEntity.ok(reelMapper.toDTO(updatedReel));
    }

    @DeleteMapping("/{reelId}")
    public ResponseEntity<Void> deleteReel(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer reelId) {
        
        User reqUser = userService.findUserByJwt(jwt);
        reelsService.deleteReel(reelId, reqUser.getId());
        return ResponseEntity.noContent().build();
    }
}
