package com.tap.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tap.social.models.Chat;
import com.tap.social.models.Message;
import com.tap.social.models.User;
import com.tap.social.repository.ChatRepository;
import com.tap.social.repository.MessageRepository;
@Service
public class MessageServiceImpl implements MessageService {
	
	private final MessageRepository messageRepository;
	
	private final ChatService chatService;
	
	private final ChatRepository chatRepository;
	
	public MessageServiceImpl(ChatService chatService, MessageRepository messageRepository, ChatRepository chatRepository) {
		this.messageRepository = messageRepository;
		this.chatService = chatService;
		this.chatRepository = chatRepository;
	}
	
	
	
	@Override
	public Message createMessage(User user, Integer chatId, Message req) {
		Chat chat=chatService.findChatById(chatId);
		Message message =new Message();
		
		
		message.setChat(chat);
		message.setContent(req.getContent());
		message.setImage(req.getImage());
		message.setSender(user);
		message.setSentAt(LocalDateTime.now());
		Message savedMessage=messageRepository.save(message);
		
		chat.getMessages().add(savedMessage);
		chatRepository.save(chat);
		return savedMessage;
	}

	@Override
	public List<Message> findChatsmessages(Integer chatId) {
		Chat chat=chatService.findChatById(chatId);
		
		
		return messageRepository.findByChatId(chatId);
	}

}
