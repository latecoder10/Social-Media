package com.tap.social.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.dto.MessageDTO;
import com.tap.social.exception.UserNotBelongsToChatException;
import com.tap.social.mapper.MessageMapper;
import com.tap.social.models.Message;
import com.tap.social.models.User;
import com.tap.social.service.ChatService;
import com.tap.social.service.MessageService;
import com.tap.social.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	private final MessageService messageService;

	private final UserService userService;
	
	private final MessageMapper messageMapper;
	
	private final ChatService chatService;

	public MessageController(MessageService messageService, UserService userService, MessageMapper messageMapper, ChatService chatService) {
		this.messageService = messageService;
		this.userService = userService;
		this.messageMapper = messageMapper;
		this.chatService = chatService;
	}

	@PostMapping("/{chatId}")
	public ResponseEntity<MessageDTO> createMessage(@RequestHeader("Authorization") String jwt, @RequestBody Message req,
			@PathVariable Integer chatId) {
		// If validation fails here, Spring will throw MethodArgumentNotValidException
		User user = userService.findUserByJwt(jwt);
		
		// Check if user is in chat
	    if (!chatService.isUserInChat(user, chatId)) {
	        throw new UserNotBelongsToChatException("User does not belong to this chat.");
	    }
		Message message = messageService.createMessage(user, chatId, req);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(messageMapper.toMessageDTO(message));
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<List<MessageDTO>> findChatsMessages(@RequestHeader("Authorization") String jwt,
			 @PathVariable Integer chatId) {
		// If validation fails here, Spring will throw MethodArgumentNotValidException
		User user = userService.findUserByJwt(jwt);
		// Check if user is in chat
	    if (!chatService.isUserInChat(user, chatId)) {
	        throw new UserNotBelongsToChatException("User does not belong to this chat.");
	    }
		List<Message> chatsmessages = messageService.findChatsmessages(chatId);
		return ResponseEntity.status(HttpStatus.FOUND).body(messageMapper.toMessageDTOList(chatsmessages));
	}

}
