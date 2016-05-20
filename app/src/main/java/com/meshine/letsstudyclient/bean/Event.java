package com.meshine.letsstudyclient.bean;

/**
 * Created by Ming on 2016/5/7.
 */
public class Event {

    public static final int NEWEST = 0;
    public static final int HOTEAST = 1;
    public static final int EATE = 2;
    public static final int RUN = 3;
    public static final int STUDY = 4;
    public static final int REPORT = 5;
    public static final int OTHERS = 6;



    private String avatar;
    private String nick;
    private String title;
    private String number;
    private String limit;
    private String date;
    private String summary;
    private String type;

    public Event(String avatar, String nick, String title, String number, String limit, String date, String summary, String type) {
        this.avatar = avatar;
        this.nick = nick;
        this.title = title;
        this.number = number;
        this.limit = limit;
        this.date = date;
        this.summary = summary;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
