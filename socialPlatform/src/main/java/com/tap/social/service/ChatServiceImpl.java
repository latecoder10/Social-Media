package com.tap.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tap.social.dto.ChatRecordDTO;
import com.tap.social.mapper.ChatMapper;
import com.tap.social.models.Chat;
import com.tap.social.models.User;
import com.tap.social.repository.ChatRepository;
import com.tap.social.repository.UserRepository;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;
    
    public ChatServiceImpl(ChatRepository chatRepository, UserRepository userRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
    }

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
    

	@Override
	public Chat createChat(User reqUser, User user2) {
		Chat isExist = chatRepository.findChatByParticipantsId(user2, reqUser);
		System.out.println(reqUser+" "+user2);
		if(isExist!=null) {
			return isExist;
		}
		
		Chat chat=new Chat();
		chat.getParticipants().add(user2);
		chat.getParticipants().add(reqUser);
		chat.setCreatedAt(LocalDateTime.now());
		return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(Integer chatId) {
		return chatRepository.findById(chatId).orElseThrow(()->  new IllegalArgumentException("No chat available with chat id"+chatId));
	}

	@Override
	public List<Chat> findUsersChat(Integer userId) {
		
		return chatRepository.findByParticipantsId(userId);
	}
	@Override
	public boolean isUserInChat(User user, Integer chatId) {
	    Chat chat = findChatById(chatId);
	    return chat.getParticipants().contains(user);
	}

}
