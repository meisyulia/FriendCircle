package com.example.friendcircle;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    protected abstract void initData();
    protected abstract void initView();

    public void setTheView(@LayoutRes int layoutId){
        LayoutInflater.from(this).inflate(layoutId,this.findViewById(R.id.baseContentView));
    }

    protected void releaseRes(){

    }

    @Override
    protected void onDestroy() {
        releaseRes();
        super.onDestroy();
    }
}