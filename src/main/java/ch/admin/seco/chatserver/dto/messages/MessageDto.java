package ch.admin.seco.chatserver.dto.messages;

import java.util.Date;

public class MessageDto {

    private final int id;
    private String message;
    private int user_id;
    private Date timestamp;

    public MessageDto(final int id, final String message, final int user_id, Date timestamp){
        this.id = id;
        this.message = message;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
