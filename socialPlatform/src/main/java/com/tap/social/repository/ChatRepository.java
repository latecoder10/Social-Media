package com.tap.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tap.social.models.Chat;
import com.tap.social.models.User;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
	public List<Chat> findByParticipantsId(Integer userId);
	
	@Query("select c from Chat c where :user Member of c.participants And :reqUser Member of c.participants")
	public Chat findChatByParticipantsId(@Param("user") User user,@Param("reqUser") User reqUser);
}
