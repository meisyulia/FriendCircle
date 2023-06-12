package com.example.friendcircle.database;

import com.example.friendcircle.bean.FriendInfo;
import com.example.friendcircle.bean.SendCirInfo;
import com.example.friendcircle.comparator.SendCirComparator;
import com.example.friendcircle.model.SendCircle;
import com.example.friendcircle.model.SendCircle_Table;
import com.example.friendcircle.model.User;
import com.example.friendcircle.model.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SendCircleHelper {
    private static final String TAG = "SendCircleHelper";

    //获取对应的姓名的朋友圈
    public ArrayList<SendCirInfo> getCirByName(String username){
        ArrayList<SendCirInfo> infoArrayList = new ArrayList<>();
        User user = SQLite.select().from(User.class).where(User_Table.username.eq(username))
                .querySingle();
        String avatar = user.getAvatar();
        List<SendCircle> sendCircles = SQLite.select().from(SendCircle.class).where(SendCircle_Table.username.eq(username))
                .queryList();
        //Log.i(TAG, "getCirByName: sendCircles="+ GsonUtil.ListToJson(sendCircles));
        for (SendCircle sendCircle : sendCircles) {
            SendCirInfo cirInfo = new SendCirInfo();
            cirInfo.setAvatar(avatar);
            cirInfo.setCircle_id(sendCircle.getId());
            cirInfo.setUsername(username);
            cirInfo.setContent(sendCircle.getContent());
            cirInfo.setPicList(sendCircle.getPicList());
            cirInfo.setSend_time(sendCircle.getSend_time());
            infoArrayList.add(cirInfo);
        }
        Collections.sort(infoArrayList,new SendCirComparator());
        return infoArrayList;
    }
    //获取某个用户的所有好友的朋友圈
    public ArrayList<SendCirInfo> getAllFriCir(String username){
        ArrayList<SendCirInfo> infoArrayList = new ArrayList<>();
        FriendHelper friHelper = new FriendHelper();
        ArrayList<FriendInfo> friList = friHelper.getFriend(username);
        for (FriendInfo friendInfo : friList) {
            String friendName = friendInfo.getFriendName();
            String avatar = friendInfo.getAvatar();
            List<SendCircle> sendCircles = SQLite.select().from(SendCircle.class).where(SendCircle_Table.username.eq(friendName))
                    .queryList();
            for (SendCircle sendCircle : sendCircles) {
                SendCirInfo cirInfo = new SendCirInfo();
                cirInfo.setAvatar(avatar);
                cirInfo.setCircle_id(sendCircle.getId());
                cirInfo.setUsername(friendName);
                cirInfo.setContent(sendCircle.getContent());
                cirInfo.setPicList(sendCircle.getPicList());
                cirInfo.setSend_time(sendCircle.getSend_time());
                infoArrayList.add(cirInfo);
            }
        }
        return infoArrayList;
    }
    //获取自己和所有好友的朋友圈
    public ArrayList<SendCirInfo> getCircle(String username){
        ArrayList<SendCirInfo> infoArrayList = new ArrayList<>();
        ArrayList<SendCirInfo> cirByName = getCirByName(username);
        infoArrayList.addAll(cirByName);
        ArrayList<SendCirInfo> allFriCir = getAllFriCir(username);
        infoArrayList.addAll(allFriCir);
        Collections.sort(infoArrayList,new SendCirComparator());
        //Log.i(TAG, "getCircle: infoArrayList="+GsonUtil.ListToJson(infoArrayList));
        return infoArrayList;
    }

    //删除某条朋友圈
    public void deleteCir(long id){
        SQLite.delete().from(SendCircle.class).where(SendCircle_Table.id.eq(id)).execute();
    }
}
