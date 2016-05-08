package com.meshine.letsstudyclient.bean;

/**
 * Created by Ming on 2016/5/7.
 */
public class Event {
    private String avatar;
    private String nick;
    private String title;
    private String number;
    private String limit;
    private String date;
    private String summary;

    public Event(String avatar, String nick, String title, String number, String limit, String date, String summary) {
        this.avatar = avatar;
        this.nick = nick;
        this.title = title;
        this.number = number;
        this.limit = limit;
        this.date = date;
        this.summary = summary;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Event{" +
                "avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", title='" + title + '\'' +
                ", number='" + number + '\'' +
                ", limit='" + limit + '\'' +
                ", date='" + date + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
