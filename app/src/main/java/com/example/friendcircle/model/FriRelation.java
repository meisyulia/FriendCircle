package com.example.friendcircle.model;

import com.example.friendcircle.database.AppDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDB.class)
public class FriRelation extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private long applicant_id;
    @Column
    private long acceptor_id;
    @Column
    private int relation_status; //0:申请了还没接受（还不是朋友）1:接受了成为了好友
    @Column
    private String application_time;
    @Column
    private String acceptor_time;
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

    public long getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(long applicant_id) {
        this.applicant_id = applicant_id;
    }

    public long getAcceptor_id() {
        return acceptor_id;
    }

    public void setAcceptor_id(long acceptor_id) {
        this.acceptor_id = acceptor_id;
    }

    public int getRelation_status() {
        return relation_status;
    }

    public void setRelation_status(int relation_status) {
        this.relation_status = relation_status;
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
