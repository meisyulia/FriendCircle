package com.example.friendcircle.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.friendcircle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterInfoView extends LinearLayout {
    @BindView(R.id.tv_tag)
    TextView tv_tag;
    @BindView(R.id.et_content)
    EditText et_content;

    public EnterInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_enter_info, this);
        ButterKnife.bind(this);
    }

    public void initItem(String tag,String hint){
        tv_tag.setText(tag);
        et_content.setHint(hint);
    }

    public void setInputType(int type){
        et_content.setInputType(type);
    }

    public String getContent(){
        return et_content.getText().toString().trim();
    }
}
