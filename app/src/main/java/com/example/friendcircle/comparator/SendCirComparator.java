package com.example.friendcircle.comparator;

import com.example.friendcircle.bean.SendCirInfo;
import com.example.friendcircle.util.DateUtil;

import java.util.Comparator;

public class SendCirComparator implements Comparator<SendCirInfo> {
    @Override
    public int compare(SendCirInfo t1, SendCirInfo t2) {
        long time1 = DateUtil.getTime(t1.getSend_time());
        long time2 = DateUtil.getTime(t2.getSend_time());
        return (int) (time2-time1);
    }
}
