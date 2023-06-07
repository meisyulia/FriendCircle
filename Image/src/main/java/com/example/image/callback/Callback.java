package com.example.image.callback;

import java.io.File;
import java.io.Serializable;

/**
 * @author liwanlian
 * @date 2023/5/29 23:22
 */
public interface Callback extends Serializable {
    void onSingleImageSelected(String path);

    void onImageSelected(String path);

    void onImageUnselected(String path);

    void onCameraShot(File imageFile);

    void onPreviewChanged(int select, int sum, boolean visible);
}
