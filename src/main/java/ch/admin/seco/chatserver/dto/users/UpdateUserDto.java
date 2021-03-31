package ch.admin.seco.chatserver.dto.users;

public class UpdateUserDto {
    private String nickname;
    private Status status;
    private Integer avatar;

    public UpdateUserDto(final String nickname, final Status status, final Integer avatar){
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

    public Integer getAvatar() {
        return avatar;
    }
}
