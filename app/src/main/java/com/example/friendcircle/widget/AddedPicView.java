package com.example.friendcircle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.friendcircle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddedPicView extends RelativeLayout {
    @BindView(R.id.iv_added_pic)
    ImageView iv_added_pic;
    @BindView(R.id.iv_delete_pic)
    ImageView iv_delete_pic;
    private int position;
    private OnPicDeleteListener onPicDeleteListener;
    private OnPicClickListener onPicClickListener;

    public AddedPicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_added_pic,this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_added_pic, R.id.iv_delete_pic})
    void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.iv_added_pic:
                if (onPicClickListener!=null){
                    onPicClickListener.onPicClick(position);
                }
                break;
            case R.id.iv_delete_pic:
                if (onPicDeleteListener!=null){
                    onPicDeleteListener.onPicDelete(position);
                }
                break;
        }
    }

    public void initPic(String imageUrl,int position){
        //加载图片
        Glide.with(this).load(imageUrl).into(iv_added_pic);
        this.position = position;
    }

    public void setIconVisible(int visibility){
        iv_delete_pic.setVisibility(visibility);
    }


    public void setOnPicDeleteListener(OnPicDeleteListener onPicDeleteListener){
        this.onPicDeleteListener = onPicDeleteListener;
    }
    public interface OnPicDeleteListener{
        void onPicDelete(int position);
    }

    public void setOnPicClickListener(OnPicClickListener onPicClickListener){
        this.onPicClickListener = onPicClickListener;
    }
    public interface OnPicClickListener{
        void onPicClick(int position);
    }
}
