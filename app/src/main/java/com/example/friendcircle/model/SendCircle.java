package com.example.friendcircle.model;

import com.example.friendcircle.database.AppDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;

@Table(database = AppDB.class)
public class SendCircle extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private String username;
    @Column
    private String content;
    @Column
    private String picList;
    @Column
    private String send_time;
    @Column
    private String extraOne;
    @Column
    private String extraTwo;
    @Column
    private String extraThree;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getExtraOne() {
        return extraOne;
    }

    public void setExtraOne(String extraOne) {
        this.extraOne = extraOne;
    }

    public String getExtraTwo() {
        return extraTwo;
    }

    public void setExtraTwo(String extraTwo) {
        this.extraTwo = extraTwo;
    }

    public String getExtraThree() {
        return extraThree;
    }

    public void setExtraThree(String extraThree) {
        this.extraThree = extraThree;
    }
}
