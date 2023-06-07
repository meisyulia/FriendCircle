package com.example.friendcircle.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.Constant.PlusType;
import com.example.friendcircle.PlusActivity;
import com.example.friendcircle.R;
import com.example.friendcircle.adapter.AddAdapter;
import com.example.friendcircle.bean.AddInfo;
import com.example.friendcircle.widget.IconItemView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FriCirFragment extends BaseFragment{

    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.rv_fri_cir)
    RecyclerView rv_fri_cir;
    private Context mContext;
    private Unbinder unbinder;
    private ArrayList<AddInfo> mAddInfos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_fri_cir, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mAddInfos = new ArrayList<>();
        mAddInfos.add(new AddInfo(R.drawable.add_friend_32,"添加好友"));
        mAddInfos.add(new AddInfo(R.drawable.firend_circle_32,"发朋友圈"));
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_add, R.id.iv_search})
    void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                showPopupWindow(view);
                break;
            case R.id.iv_search:
                break;
        }
    }

    private void showPopupWindow(View view) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.popup_add, null);
        PopupWindow popupWindow = new PopupWindow(v);
        popupWindow.setWidth(400);
        popupWindow.setHeight(260);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(view);
        initPopupView(v,popupWindow);
    }

    private void initPopupView(View v,PopupWindow popupWindow) {
        RecyclerView rv_add = v.findViewById(R.id.rv_add);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_add.setLayoutManager(layoutManager);
        AddAdapter addAdapter = new AddAdapter(mContext, mAddInfos);
        rv_add.setAdapter(addAdapter);
        addAdapter.setOnItemClickListener(((position, title) -> {
            if (TextUtils.equals(title,"添加好友")){
                showTips("添加好友");
                //跳转
                goToOther(PlusActivity.class, PlusType.ADD_FRIEND);
                popupWindow.dismiss();
            }else if (TextUtils.equals(title,"发朋友圈")){
                showTips("发送朋友圈");
                goToOther(PlusActivity.class, PlusType.SEND_CIRCLE);
                popupWindow.dismiss();
            }
        }));
    }

    private void goToOther(Class<?> cls, PlusType type) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtra("switch_page",type);
        startActivity(intent);
    }
}
