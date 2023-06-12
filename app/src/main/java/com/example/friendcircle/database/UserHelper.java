package com.example.friendcircle.database;


import com.example.friendcircle.model.User;
import com.example.friendcircle.model.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class UserHelper {

    //更新用户密码
    public void updateUser(String username,String password){
        SQLite.update(User.class).set(User_Table.password.eq(password)).where(User_Table.username.eq(username)).execute();
    }

    //查询用户
    public User selectByUsername(String username){
        return SQLite.select().from(User.class).where(User_Table.username.eq(username)).querySingle();
    }

    //删除用户
    public void deleteByUsername(String username){
        SQLite.delete(User.class).where(User_Table.username.eq(username)).execute();
    }
    //获取所有列的数目
    public int getAllCount(){
        return SQLite.select().from(User.class).where().queryList().size();
    }


}
