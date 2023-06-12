package com.example.friendcircle.database;

import com.example.friendcircle.bean.FriendInfo;
import com.example.friendcircle.comparator.FriComparator;
import com.example.friendcircle.model.FriRelation;
import com.example.friendcircle.model.FriRelation_Table;
import com.example.friendcircle.model.User;
import com.example.friendcircle.model.User_Table;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FriendHelper {
    private static final String TAG = "FriendHelper";

    //查询可以添加的好友
    public ArrayList<FriendInfo> getAddFri(String username){
        ArrayList<FriendInfo> addFriList = new ArrayList<>();
        List<User> users = SQLite.select().from(User.class).where(User_Table.username.notEq(username))
                .queryList();
        for (User user : users) {
            FriendInfo friendInfo = new FriendInfo();
            friendInfo.setAvatar(user.getAvatar());
            friendInfo.setFriend_id(user.getId());
            friendInfo.setFriendName(user.getUsername());
            friendInfo.setRelation(0);
            addFriList.add(friendInfo);
        }
        ArrayList<FriendInfo> otherFriList = new ArrayList<>();
        ArrayList<FriendInfo> applicaFri = getApplicaFri(username);
        otherFriList.addAll(applicaFri);
        ArrayList<FriendInfo> acceptFri = getAcceptFri(username);
        otherFriList.addAll(acceptFri);
        ArrayList<FriendInfo> friend = getFriend(username);
        otherFriList.addAll(friend);
        addFriList.removeIf(objB->otherFriList.stream().anyMatch(objA->objA.getFriendName().equals(objB.getFriendName())));
        //Log.i(TAG, "getAddFri: addFriList="+ GsonUtil.toJsonString(addFriList));
        return addFriList;
    }
    //查询已申请为好友的数组列表
    public ArrayList<FriendInfo> getApplicaFri(String username){
        ArrayList<FriendInfo> applicaFri = new ArrayList<>();
        //先获取本人已经申请的好友id
        List<FriRelation> relationList = SQLite.select()
                .from(FriRelation.class).as("A")
                .innerJoin(User.class).as("C")
                .on((User_Table.id.withTable(NameAlias.of("C"))).eq(FriRelation_Table.applicant_id))
                .where(User_Table.username.eq(username))
                .and(FriRelation_Table.relation_status.eq(0))
                .queryList();
        //Log.i(TAG, "getApplicaFri: relationList.size="+relationList.size());
        for (FriRelation friRelation : relationList) {
            User user = SQLite.select().from(User.class)
                    .where(User_Table.id.eq(friRelation.getAcceptor_id()))
                    .querySingle();
            if (user != null) {
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setAvatar(user.getAvatar());
                friendInfo.setFriend_id(user.getId());
                friendInfo.setFriendName(user.getUsername());
                friendInfo.setApplication_time(friRelation.getApplication_time());
                friendInfo.setRelation(1);
                applicaFri.add(friendInfo);
            }
        }
        return applicaFri;
    }
    //查询附近的人
    public ArrayList<FriendInfo> getNearBy(String username){
        ArrayList<FriendInfo> nearByList = new ArrayList<>();
        nearByList.addAll(getAddFri(username));
        nearByList.addAll(getApplicaFri(username));
        return nearByList;
    }
    //查询自己是接受方的列表，还没成为好友的
    public ArrayList<FriendInfo> getAcceptFri(String username){
        ArrayList<FriendInfo> accepFriList = new ArrayList<>();
        //先获取自己是接受方的好友id
        List<FriRelation> relationList = SQLite.select()
                .from(FriRelation.class).as("A")
                .innerJoin(User.class).as("C")
                .on((User_Table.id.withTable(NameAlias.of("C"))).eq(FriRelation_Table.acceptor_id))
                .where(User_Table.username.eq(username))
                .and(FriRelation_Table.relation_status.eq(0))
                .queryList();
        for (FriRelation friRelation : relationList) {
            User user = SQLite.select().from(User.class)
                    .where(User_Table.id.eq(friRelation.getApplicant_id()))
                    .querySingle();
            if (user != null) {
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setAvatar(user.getAvatar());
                friendInfo.setFriend_id(user.getId());
                friendInfo.setFriendName(user.getUsername());
                friendInfo.setApplication_time(friRelation.getApplication_time());
                friendInfo.setRelation(2);
                accepFriList.add(friendInfo);
            }
        }
        return accepFriList;
    }
    //查询已经成为好友的
    public ArrayList<FriendInfo> getFriend(String username){
        ArrayList<FriendInfo> friendInfos = new ArrayList<>();
        //查询自己作为申请人的
        List<FriRelation> relationList = SQLite.select()
                .from(FriRelation.class)
                .innerJoin(User.class).as("C")
                .on((User_Table.id.withTable(NameAlias.of("C"))).eq(FriRelation_Table.applicant_id))
                .where(User_Table.username.eq(username))
                .and(FriRelation_Table.relation_status.eq(1))
                .queryList();
        //Log.i(TAG, "getFriend: relationList.size()="+relationList.size());
        for (FriRelation friRelation : relationList) {
            //Log.i(TAG, "getFriend: friRelation="+ GsonUtil.toJsonString(friRelation));
            User user = SQLite.select().from(User.class)
                    .where(User_Table.id.eq(friRelation.getAcceptor_id()))
                    .querySingle();
            if (user != null) {
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setAvatar(user.getAvatar());
                friendInfo.setFriend_id(user.getId());
                friendInfo.setFriendName(user.getUsername());
                friendInfo.setApplication_time(friRelation.getApplication_time());
                friendInfo.setAcceptor_time(friRelation.getAcceptor_time());
                friendInfo.setRelation(3);
                friendInfos.add(friendInfo);
            }
        }
        //查询自己作为接受人的
        List<FriRelation> relationList1 = SQLite.select()
                .from(FriRelation.class)
                .innerJoin(User.class).as("C")
                .on((User_Table.id.withTable(NameAlias.of("C"))).eq(FriRelation_Table.acceptor_id))
                .where(User_Table.username.eq(username))
                .and(FriRelation_Table.relation_status.eq(1))
                .queryList();
        for (FriRelation friRelation : relationList1) {
            User user = SQLite.select().from(User.class)
                    .where(User_Table.id.eq(friRelation.getApplicant_id()))
                    .querySingle();
            if (user != null) {
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setAvatar(user.getAvatar());
                friendInfo.setFriend_id(user.getId());
                friendInfo.setFriendName(user.getUsername());
                friendInfo.setApplication_time(friRelation.getApplication_time());
                friendInfo.setAcceptor_time(friRelation.getAcceptor_time());
                friendInfo.setRelation(3);
                friendInfos.add(friendInfo);
            }
        }
        Collections.sort(friendInfos,new FriComparator());
        return friendInfos;
    }

    //更新好友关系
    public void updateFriRel(long appli_id,long accep_id,int status,String accep_time){
        SQLite.update(FriRelation.class)
                .set(FriRelation_Table.relation_status.eq(status),FriRelation_Table.acceptor_time.eq(accep_time))
                .where(FriRelation_Table.applicant_id.eq(appli_id))
                .and(FriRelation_Table.acceptor_id.eq(accep_id))
                .execute();
    }
}
