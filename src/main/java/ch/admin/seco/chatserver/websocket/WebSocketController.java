package ch.admin.seco.chatserver.websocket;

import ch.admin.seco.chatserver.dto.messages.CreateMessageDto;
import ch.admin.seco.chatserver.dto.messages.MessageDto;
import ch.admin.seco.chatserver.dto.messages.UpdateMessageDto;
import ch.admin.seco.chatserver.dto.users.CreateUserDto;
import ch.admin.seco.chatserver.service.MessageService;
import ch.admin.seco.chatserver.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketController extends TextWebSocketHandler {

    private final List<WebSocketSession> activeSessions = new CopyOnWriteArrayList<>(); //Avoid overwriting threads in any case
    private final ObjectMapper objectMapper;
    private final MessageService messageService;
    private final UserService userService;

    public WebSocketController(final ObjectMapper objectMapper, final MessageService messageService, final UserService userService) {
        this.objectMapper = objectMapper;
        this.messageService = messageService;
        this.userService = userService;
    }

    // Sends active session into the array list
    public <T> void sendPayload(final String action, final T payload) {
        System.out.println("Websocket message sent");
        try {
            final String res = objectMapper.writeValueAsString(Message.of(action, payload));
            for (final WebSocketSession activeSession : activeSessions) {
                activeSession.sendMessage(new TextMessage(res));
            }
        } catch (final IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        // Handle the text message when an user gets added
        final CreateUserDto createUserDto = objectMapper.readValue(message.getPayload(), CreateUserDto.class);
        userService.createUser(createUserDto);
        sendPayload("user_added", createUserDto);

        // Handle text message when a new message is sent
        final CreateMessageDto createMessageDto = objectMapper.readValue(message.getPayload(), CreateMessageDto.class);
        messageService.createMessage(createMessageDto);
        sendPayload("message_added", createMessageDto);
    }


    // Responsible for opening and closing of each session
    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        activeSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        activeSessions.remove(session);
    }

    // <T> defines a generic class, meaning any datatype is valid and the object is independent of its datatype
    private static final class Message<T> {

        private final String action;
        private final T data;

        public Message(final String action, final T data) {
            this.action = action;
            this.data = data;
        }

        public static <T> Message<T> of(final String action, final T data) {
            return new Message<>(action, data);
        }

        public String getAction() {
            return action;
        }

        public T getData() {
            return data;
        }
    }
}
