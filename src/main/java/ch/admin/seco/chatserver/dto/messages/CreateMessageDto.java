package ch.admin.seco.chatserver.dto.messages;

import java.time.Instant;

public class CreateMessageDto {

    private String message;
    private int user_id;
    private Instant timestamp;

    CreateMessageDto(final String message, final int user_id, Instant timestamp){
        this.message = message;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
