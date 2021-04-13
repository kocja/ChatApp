package ch.admin.seco.chatserver.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketController webSocketController;

    public WebSocketConfiguration(final WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }


    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketController, "/ws").setAllowedOrigins("*");
    }
}
