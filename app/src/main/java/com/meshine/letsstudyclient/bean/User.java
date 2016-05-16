package com.meshine.letsstudyclient.bean;

/**
 * Created by Ming on 2016/4/24.
 */
public class User {

    private String username;
    private String password;
    private String nick;
    private String avatar;
    private int vitality;

    public User(String username, String password, String nick, String avatar, int vitality) {
        this.username = username;
        this.password = password;
        this.nick = nick;
        this.avatar = avatar;
        this.vitality = vitality;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }
}
