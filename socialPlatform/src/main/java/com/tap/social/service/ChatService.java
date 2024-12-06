package com.tap.social.service;

import java.util.List;

import com.tap.social.models.Chat;
import com.tap.social.models.User;

public interface ChatService {

	Chat createChat(Chat chat);

	Chat createChat(User reqUser, User user2);

	Chat findChatById(Integer chatId);

	List<Chat> findUsersChat(Integer userId);

	boolean isUserInChat(User user, Integer chatId);

}
