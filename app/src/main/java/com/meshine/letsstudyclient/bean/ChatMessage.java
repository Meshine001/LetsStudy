package com.meshine.letsstudyclient.bean;

/**
 * Created by Meshine on 16/4/28.
 */
public class ChatMessage {

    private String sender;

    private String receiver;

    private String text;

    private int type;

    private int displayPos;

    public ChatMessage(String sender, String receiver, String text, int type, int displayPos) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.type = type;
        this.displayPos = displayPos;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getDisplayPos() {
        return displayPos;
    }

    public void setDisplayPos(int displayPos) {
        this.displayPos = displayPos;
    }
}
