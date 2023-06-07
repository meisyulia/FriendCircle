package com.example.image.callback;

import com.example.image.model.Folder;

/**
 * @author liwanlian
 * @date 2023/5/29 23:23
 */
public interface OnFolderChangeListener {
    void onChange(int position, Folder folder);
}
