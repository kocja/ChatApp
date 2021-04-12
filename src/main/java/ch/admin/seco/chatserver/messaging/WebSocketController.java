package ch.admin.seco.chatserver.messaging;

import ch.admin.seco.chatserver.dto.messages.MessageDto;
import ch.admin.seco.chatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@Controller
@Transactional
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void setSimpMessagingTemplate(UserService userService){
        this.userService = userService;
    }

    public void sendMessage(MessageDto messageDto){
        String destination = "/queue";
        simpMessagingTemplate.convertAndSend(messageDto.getMessage(), destination);
        System.out.println("Message: " + messageDto.getMessage() +  " sent: " + destination);
    }
}
