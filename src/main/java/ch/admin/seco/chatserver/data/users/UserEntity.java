package ch.admin.seco.chatserver.data.users;

import ch.admin.seco.chatserver.dto.users.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private int id;

    private String nickname;
    private Status status;
    private int avatar;

    public UserEntity(final String nickname, final Status status, final int avatar){
        this.nickname = nickname;
        this.status = status;
        this.avatar = avatar;
    }

    protected UserEntity(){}


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
