package com.example.friendcircle.comparator;

import com.example.friendcircle.bean.FriendInfo;
import com.example.friendcircle.util.DateUtil;

import java.util.Comparator;

public class FriComparator implements Comparator<FriendInfo> {
    @Override
    public int compare(FriendInfo t1, FriendInfo t2) {
        long time1 = DateUtil.getTime(t1.getAcceptor_time());
        long time2 = DateUtil.getTime(t2.getAcceptor_time());
        return (int) (time2-time1);
    }
}
