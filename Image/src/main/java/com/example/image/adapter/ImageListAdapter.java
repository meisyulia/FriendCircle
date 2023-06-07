package com.example.image.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.image.ISNav;
import com.example.image.R;
import com.example.image.adapter.base.EasyRVAdapter;
import com.example.image.adapter.holder.EasyRVHolder;
import com.example.image.callback.OnItemClickListener;
import com.example.image.model.Image;
import com.example.image.model.config.ISListConfig;
import com.example.image.utils.Constant;

import java.io.File;
import java.util.List;

/**
 * @author liwanlian
 * @date 2023/5/29 23:41
 */
public class ImageListAdapter extends EasyRVAdapter<Image> {
    private boolean showCamera;
    private boolean mutiSelect;

    private ISListConfig config;
    private Context context;
    private OnItemClickListener listener;

    public ImageListAdapter(Context context, List<Image> list, ISListConfig config) {
        super(context, list, R.layout.item_img_sel, R.layout.item_img_sel_take_photo);
        this.context = context;
        this.config = config;
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final Image item) {

        if (position == 0 && showCamera) {
            ImageView iv = viewHolder.getView(R.id.ivTakePhoto);
            iv.setImageResource(R.drawable.ic_take_photo);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onImageClick(position, item);
                }
            });
            return;
        }

        if (mutiSelect) {
            viewHolder.getView(R.id.ivPhotoCheaked).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int ret = listener.onCheckedClick(position, item);
                        if (ret == 1) { // 局部刷新
                            if (Constant.imageList.contains(item.getPath())) {
                                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_checked);
                            } else {
                                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_uncheck);
                            }
                        }
                    }
                }
            });
        }

        viewHolder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onImageClick(position, item);
            }
        });

        final ImageView iv = viewHolder.getView(R.id.ivImage);
        File file = new File(item.getPath());
        Log.i("lll", "file exit==>" + file.exists());
        ISNav.getInstance().displayImage(context, item.getPath(), iv);

        if (mutiSelect) {
            viewHolder.setVisible(R.id.ivPhotoCheaked, true);
            if (Constant.imageList.contains(item.getPath())) {
                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_checked);
            } else {
                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_uncheck);
            }
        } else {
            viewHolder.setVisible(R.id.ivPhotoCheaked, false);
        }
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public void setMutiSelect(boolean mutiSelect) {
        this.mutiSelect = mutiSelect;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && showCamera) {
            return 1;
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
