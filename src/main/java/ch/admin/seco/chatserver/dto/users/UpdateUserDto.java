package ch.admin.seco.chatserver.dto.users;

import ch.admin.seco.chatserver.dto.users.status.Status;

public class UpdateUserDto {

    // Declared final, user already exists
    private final String nickname;
    private final Status status;
    private final Integer avatar;

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
