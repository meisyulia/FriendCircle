package com.example.friendcircle;

import android.app.Application;

import com.bumptech.glide.Glide;
//import com.example.image.ISNav;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.yuyh.library.imgsel.ISNav;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //不添加加载不了图片
        ISNav.getInstance().init((context, path, imageView) -> {

            Glide.with(context).load(path).into(imageView);
        });
        FlowManager.init(this);
    }
}
