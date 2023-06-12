package com.example.friendcircle.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

public class ImageUtil {
    private static final String TAG = "ImageUtil";

    //加载头像
    public static void loadAvatarImage(Context context, String imageUrl, ImageView view,int resId){
        if (TextUtils.isEmpty(imageUrl)){
            //默认头像
            Glide.with(context).load(resId)
                    .signature(new ObjectKey(System.currentTimeMillis()));
        }else{
            Glide.with(context).load(imageUrl).signature(new ObjectKey(System.currentTimeMillis())).into(view);
        }

    }
    //加载图片
    public static void loadImage(Context context,String imageUrl,ImageView view){
        Glide.with(context).load(imageUrl).into(view);
    }
}
