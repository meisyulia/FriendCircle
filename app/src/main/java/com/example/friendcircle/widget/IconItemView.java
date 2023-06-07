package com.example.friendcircle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.friendcircle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IconItemView extends LinearLayout {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.v_line)
    View v_line;
    @BindView(R.id.ll_item)
    LinearLayout ll_item;

    public IconItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_icon_item,this);
        ButterKnife.bind(this);
    }

    public void initItem(int resId,String title){
        iv_icon.setImageResource(resId);
        tv_title.setText(title);
    }

    public void setLineVisibility(int visibility){
        v_line.setVisibility(visibility);
    }
}
