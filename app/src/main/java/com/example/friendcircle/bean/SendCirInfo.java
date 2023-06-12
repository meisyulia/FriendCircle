package com.example.friendcircle.bean;

public class SendCirInfo {
    private String avatar;
    private long circle_id;
    private String username;
    private String content;
    private String picList;
    private String send_time;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(long circle_id) {
        this.circle_id = circle_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}
