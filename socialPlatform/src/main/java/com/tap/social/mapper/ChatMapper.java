package com.tap.social.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tap.social.dto.ChatRecordDTO;
import com.tap.social.models.Chat;
import com.tap.social.models.User;

@Component
public class ChatMapper {
	private final MessageMapper messageMapper;
	
	public ChatMapper(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

    public ChatRecordDTO toChatRecordDTO(Chat chat) {
        return new ChatRecordDTO(
                chat.getId(),
                chat.getChatName(),
                chat.getChatImage(),
                chat.getParticipants().stream().map(User::getId).collect(Collectors.toList()),
                chat.getMessages().stream().map(messageMapper::toMessageDTO).collect(Collectors.toList()),
                chat.getCreatedAt()
        );
    }

//    public Chat toChatEntity(ChatRecordDTO chatDTO, List<User> participants) {
//        Chat chat = new Chat();
//        chat.setChatName(chatDTO.chatName());
//        chat.setChatImage(chatDTO.chatImage());
//        chat.setParticipants(new HashSet<>(participants));
//        return chat;
//    }
    
    // Method to convert a list of Chat entities to List<ChatRecordDTO>
    public List<ChatRecordDTO> toChatRecordDTOList(List<Chat> chats) {
        return chats.stream()
                    .map(this::toChatRecordDTO)
                    .collect(Collectors.toList());
    }
}
