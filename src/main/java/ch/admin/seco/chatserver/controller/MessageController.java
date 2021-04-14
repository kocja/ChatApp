package ch.admin.seco.chatserver.controller;

import ch.admin.seco.chatserver.data.messages.MessageRepository;
import ch.admin.seco.chatserver.dto.messages.CreateMessageDto;
import ch.admin.seco.chatserver.dto.messages.MessageDto;
import ch.admin.seco.chatserver.service.MessageService;
import ch.admin.seco.chatserver.websocket.WebSocketController;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/message")
public class MessageController {

    private final MessageService messageService;
    private final WebSocketController webSocketController;

    public MessageController(final MessageService messageService, WebSocketController webSocketController) {
        this.messageService = messageService;
        this.webSocketController = webSocketController;
    }

    @GetMapping
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("{id}")
    public MessageDto getMessageById (@PathVariable final int id){
        return messageService.getMessageById(id);
    }


    @PostMapping
    public MessageDto createMessage(@RequestBody final CreateMessageDto messageDto) {
        webSocketController.sendPayload("message_added", messageService.createMessage(messageDto));
        return null;
    }

    @DeleteMapping
    public void deleteAllMessages(){
        messageService.deleteAllMessages();
    }
}


