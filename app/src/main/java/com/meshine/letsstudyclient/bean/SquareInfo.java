package com.meshine.letsstudyclient.bean;

/**
 * Created by Ming on 2016/4/24.
 */
public class SquareInfo {
    private String avatar;
    private String title;
    private String details;

    public SquareInfo(String avatar, String title, String details) {
        this.avatar = avatar;
        this.title = title;
        this.details = details;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
