package com.tap.social.service;

import java.util.List;

import com.tap.social.models.Message;
import com.tap.social.models.User;

public interface MessageService {

	List<Message> findChatsmessages(Integer chatId);

	Message createMessage(User user, Integer chatId, Message message);

}
