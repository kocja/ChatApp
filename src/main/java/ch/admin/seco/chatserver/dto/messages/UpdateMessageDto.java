package ch.admin.seco.chatserver.dto.messages;

public class UpdateMessageDto {

    private String message;
    private int user_id;

    UpdateMessageDto(final String message, final Integer user_id){
        this.message = message;
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
