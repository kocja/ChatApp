package ch.admin.seco.chatserver.dto.users;

public class UserDto {

    /*
    User Objekte erstellen, getter und setter.
     */

    private final int id;
    private String nickname;
    private Status status;
    private int avatar;

    public UserDto(final int id, final String nickname, final Status status, final int avatar){
        this.id = id;
        this.nickname = nickname;
        this.status = status;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

}
