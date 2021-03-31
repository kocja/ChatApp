package ch.admin.seco.chatserver.dto.messages;

public class MessageDto {
    private final int id;
    private String message;
    private int user_id;

    public MessageDto(final int id, final String message, final int user_id){
        this.id = id;
        this.message = message;
        this.user_id = user_id;
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
