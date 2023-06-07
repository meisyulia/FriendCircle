package com.example.friendcircle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.friendcircle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentView extends LinearLayout {
    @BindView(R.id.tv_content_title)
    TextView tv_content_title;
    @BindView(R.id.tv_content)
    EditText tv_content;

    public ContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_content,this);
        ButterKnife.bind(this);
    }

    public void initItem(String title,String content){
        tv_content_title.setText(title);
        tv_content.setText(content);
    }

    public void setEditable(boolean isEdit){
        tv_content.setEnabled(isEdit);
    }

    public String getEditContent(){
        return tv_content.getText().toString().trim();
    }
}
