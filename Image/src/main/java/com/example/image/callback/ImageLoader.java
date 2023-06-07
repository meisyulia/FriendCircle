package com.example.image.callback;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * @author liwanlian
 * @date 2023/5/29 23:23
 */
public interface ImageLoader extends Serializable {
    void displayImage(Context context, String path, ImageView imageView);
}
