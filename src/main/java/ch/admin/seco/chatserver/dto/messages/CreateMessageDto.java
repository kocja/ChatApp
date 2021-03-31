package ch.admin.seco.chatserver.dto.messages;

public class CreateMessageDto {
    private String message;
    private int user_id;

    CreateMessageDto(final String message, final int user_id){
        this.message = message;
        this.user_id = user_id;
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
