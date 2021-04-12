package ch.admin.seco.chatserver.controller;

import ch.admin.seco.chatserver.dto.messages.CreateMessageDto;
import ch.admin.seco.chatserver.dto.messages.MessageDto;
import ch.admin.seco.chatserver.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/message")
public class MessageController {

    private MessageService messageService;

    public MessageController(final MessageService messageService) {
        this.messageService = messageService;
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
    public MessageDto createMessage(@RequestBody CreateMessageDto messageDto) {
        return messageService.createMessage(messageDto);
    }


    /*
    @PutMapping("{id}")
    public MessageDto updateMessage(@PathVariable final int id, @RequestBody UpdateMessageDto messageDto){
        return messageService.updateMessage(id, messageDto);
    }*/

}


