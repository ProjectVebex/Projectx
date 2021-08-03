package com.example.the_project.model;

public class Messages {
    String message;
    String senderId;
    long timeStamp;

    public Messages(String message, String senderId, long timeStamp) {
        this.message = message;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderId;
    }

    public void setSenderid(String senderid) {
        this.senderId = senderid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
