package com.tap.social.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tap.social.dto.MessageDTO;
import com.tap.social.models.Message;
import com.tap.social.models.User;

@Component
public class MessageMapper {

    public MessageDTO toMessageDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getContent(),
                message.getImage(),
                message.getChat().getId(), // Only include chatId instead of full Chat object
                message.getSender().getId(), // Only include senderId instead of full User object
                message.getSentAt()
        );
    }

    public Message toMessageEntity(MessageDTO messageDTO, User sender) {
        Message message = new Message();
        message.setContent(messageDTO.content());
        message.setSender(sender);
        return message;
    }
    
    public List<MessageDTO> toMessageDTOList(List<Message> messages){
    		return messages.stream().map(this::toMessageDTO).collect(Collectors.toList());
    }
}
