package com.example.friendcircle.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.friendcircle.widget.CustomRoundAngleImageView;

public class ImageUtil {
    private static final String TAG = "ImageUtil";

    public static void loadAvatarImage(Context context, String imageUrl, ImageView view){
        Log.i(TAG, "loadAvatarImage: view 是否是自定义的="+(view instanceof CustomRoundAngleImageView));
        Glide.with(context).load(imageUrl).into(view);
        /*if (view instanceof CustomRoundAngleImageView){
            Glide.with(context).load(imageUrl).into(((CustomRoundAngleImageView) view));
        }else{
            Glide.with(context).load(imageUrl).into(view);
        }*/

    }
}
