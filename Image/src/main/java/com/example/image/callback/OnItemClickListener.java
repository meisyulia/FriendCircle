package com.example.image.callback;

import com.example.image.model.Image;

/**
 * @author liwanlian
 * @date 2023/5/29 23:24
 */
public interface OnItemClickListener {
    int onCheckedClick(int position, Image image);

    void onImageClick(int position, Image image);
}
