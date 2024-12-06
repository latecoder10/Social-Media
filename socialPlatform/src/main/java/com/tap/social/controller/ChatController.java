package com.tap.social.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.dto.ChatRecordDTO;
import com.tap.social.mapper.ChatMapper;
import com.tap.social.models.Chat;
import com.tap.social.models.User;
import com.tap.social.repository.UserRepository;
import com.tap.social.request.CreateChatRequest;
import com.tap.social.service.ChatService;
import com.tap.social.service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

	private final ChatService chatService;
	
	private final UserService userService;
	
	private final ChatMapper chatMapper;
	public ChatController(ChatService chatService, UserRepository userRepository, ChatMapper chatMapper, UserService userService) {
		this.chatService = chatService;
		this.userService = userService;
		this.chatMapper = chatMapper;
	}

	@PostMapping("/")
	public ResponseEntity<ChatRecordDTO> createChat(@RequestHeader("Authorization") String jwt,@RequestBody CreateChatRequest req) {
		User reqUser=userService.findUserByJwt(jwt);
		User user2=userService.findUserByUserId(req.getUserId());
		Chat chat =chatService.createChat(reqUser, user2);
		
		return ResponseEntity.ok(chatMapper.toChatRecordDTO(chat));
	}

	@GetMapping
	public ResponseEntity<List<ChatRecordDTO>> findUserChat(@RequestHeader("Authorization") String jwt) {
		User user=userService.findUserByJwt(jwt);
		List<Chat> usersChat = chatService.findUsersChat(user.getId());
		return new ResponseEntity<>(chatMapper.toChatRecordDTOList(usersChat),HttpStatus.ACCEPTED);
	}
}
