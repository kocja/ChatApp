package ch.admin.seco.chatserver.controller;

import ch.admin.seco.chatserver.dto.messages.CreateMessageDto;
import ch.admin.seco.chatserver.dto.messages.MessageDto;
import ch.admin.seco.chatserver.service.MessageService;
import ch.admin.seco.chatserver.websocket.WebSocketController;
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
    public MessageDto getMessageById(@PathVariable final int id) {
        return messageService.getMessageById(id);
    }


    @PostMapping
    public MessageDto createMessage(@RequestBody final CreateMessageDto createMessageDto) {

        final MessageDto messageDto = messageService.createMessage(createMessageDto);
        webSocketController.sendPayload("message_added", messageDto);
        return messageDto;
    }

    @DeleteMapping
    public void deleteAllMessages() {
        messageService.deleteAllMessages();
    }
}


