package ch.admin.seco.chatserver.dto.users;

public class CreateUserDto {
    private String nickname;
    private Status status;
    private int avatar;

    public CreateUserDto(final String nickname, final Status status, final int avatar){
        this.nickname = nickname;
        this.status = status;
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public Status getStatus() {
        return status;
    }

    public int getAvatar() {
        return avatar;
    }

}
