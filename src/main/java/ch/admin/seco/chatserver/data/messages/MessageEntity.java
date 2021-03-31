package ch.admin.seco.chatserver.data.messages;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MessageEntity {
    @Id
    @GeneratedValue
    private int id;

    private String message;
    private int user_id;

    public MessageEntity(String message, int user_id) {
        this.message = message;
        this.user_id = user_id;
    }

    protected MessageEntity() {

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

}
