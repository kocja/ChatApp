package ch.admin.seco.chatserver.dto.users;

public class UpdateUserDto {
    private final String NICKNAME;
    private final Status STATUS;
    private final Integer AVATAR;

    public UpdateUserDto(final String nickname, final Status status, final Integer avatar){
        this.NICKNAME = nickname;
        this.STATUS = status;
        this.AVATAR = avatar;
    }

    public String getNickname() {
        return NICKNAME;
    }

    public Status getStatus() {
        return STATUS;
    }

    public Integer getAvatar() {
        return AVATAR;
    }
}
