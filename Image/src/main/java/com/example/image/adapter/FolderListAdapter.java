package com.example.image.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.image.ISNav;
import com.example.image.R;
import com.example.image.adapter.base.EasyLVAdapter;
import com.example.image.adapter.holder.EasyLVHolder;
import com.example.image.callback.OnFolderChangeListener;
import com.example.image.model.Folder;
import com.example.image.model.config.ISListConfig;

import java.util.List;

/**
 * @author liwanlian
 * @date 2023/5/29 23:38
 */
public class FolderListAdapter extends EasyLVAdapter<Folder> {
    private Context context;
    private List<Folder> folderList;
    private ISListConfig config;

    private int selected = 0;
    private OnFolderChangeListener listener;

    public FolderListAdapter(Context context, List<Folder> folderList, ISListConfig config) {
        super(context, folderList, R.layout.item_img_sel_folder);
        this.context = context;
        this.folderList = folderList;
        this.config = config;
    }

    @Override
    public void convert(EasyLVHolder holder, final int position, Folder folder) {
        if (position == 0) {
            holder.setText(R.id.tvFolderName, "所有图片")
                    .setText(R.id.tvImageNum, "共" + getTotalImageSize() + "张");
            ImageView ivFolder = holder.getView(R.id.ivFolder);
            if (folderList.size() > 0) {
                ISNav.getInstance().displayImage(context, folder.getCover().getPath(), ivFolder);
            }
        } else {
            holder.setText(R.id.tvFolderName, folder.getName())
                    .setText(R.id.tvImageNum, "共" + folder.getImages().size() + "张");
            ImageView ivFolder = holder.getView(R.id.ivFolder);
            if (folderList.size() > 0) {
                ISNav.getInstance().displayImage(context, folder.getCover().getPath(), ivFolder);
            }
        }

        holder.setVisible(R.id.viewLine, position != getCount() - 1);

        if (selected == position) {
            holder.setVisible(R.id.indicator, true);
        } else {
            holder.setVisible(R.id.indicator, false);
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectIndex(position);
            }
        });
    }

    public void setData(List<Folder> folders) {
        folderList.clear();
        if (folders != null && folders.size() > 0) {
            folderList.addAll(folders);
        }
        notifyDataSetChanged();
    }

    private int getTotalImageSize() {
        int result = 0;
        if (folderList != null && folderList.size() > 0) {
            for (Folder folder : folderList) {
                result += folder.getImages().size();
            }
        }
        return result;
    }

    public void setSelectIndex(int position) {
        if (selected == position)
            return;
        if (listener != null)
            listener.onChange(position, folderList.get(position));
        selected = position;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return selected;
    }

    public void setOnFloderChangeListener(OnFolderChangeListener listener) {
        this.listener = listener;
    }
}
