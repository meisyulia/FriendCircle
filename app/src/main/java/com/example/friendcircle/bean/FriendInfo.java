package com.example.friendcircle.bean;

public class FriendInfo {
    private String avatar;
    private long friend_id;
    private String friendName;
    private int relation; //0：添加好友，1；已申请；2：接受，3：已是好友
    private String application_time;
    private String acceptor_time;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(long friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getApplication_time() {
        return application_time;
    }

    public void setApplication_time(String application_time) {
        this.application_time = application_time;
    }

    public String getAcceptor_time() {
        return acceptor_time;
    }

    public void setAcceptor_time(String acceptor_time) {
        this.acceptor_time = acceptor_time;
    }
}
