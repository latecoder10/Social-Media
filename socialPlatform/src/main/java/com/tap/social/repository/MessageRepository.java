package com.tap.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.social.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	List<Message> findByChatId(Integer chatId);
}
