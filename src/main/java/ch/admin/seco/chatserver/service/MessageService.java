package ch.admin.seco.chatserver.service;

import ch.admin.seco.chatserver.data.messages.MessageEntity;
import ch.admin.seco.chatserver.data.messages.MessageRepository;
import ch.admin.seco.chatserver.dto.messages.CreateMessageDto;
import ch.admin.seco.chatserver.dto.messages.MessageDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Returns a list of messages converted into message Dtos
    private static List<MessageDto> mapToDto(final List<MessageEntity> messageEntities) {
        final List<MessageDto> messages = new ArrayList<>();
        for (final MessageEntity messageEntity : messageEntities) {
            messages.add(mapToDto(messageEntity));
        }
        return messages;
    }

    // Map object to message dto.
    private static MessageDto mapToDto(final MessageEntity messageEntity) {
        return new MessageDto(messageEntity.getId(), messageEntity.getMessage(), messageEntity.getUser_id(), messageEntity.getTimestamp());
    }

    public List<MessageDto> getAllMessages() {
        return mapToDto(messageRepository.findAll());
    }

    public MessageDto getMessageById(final int id) {
        return mapToDto(messageRepository.getOne(id));
    }

    public MessageDto createMessage(CreateMessageDto messageDto) {
        final MessageEntity messageEntity = messageRepository.save(
                new MessageEntity(
                        messageDto.getMessage(),
                        messageDto.getUser_id(),
                        messageDto.getTimestamp()
                ));
        return mapToDto(messageEntity);
    }

    public void deleteAllMessages(){
        messageRepository.deleteAll();
    }
}
