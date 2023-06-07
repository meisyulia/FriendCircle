package com.example.friendcircle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.friendcircle.LoginRegisterActivity;
import com.example.friendcircle.R;
import com.example.friendcircle.database.UserHelper;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.EnterInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LogoutFragment extends BaseFragment{

    @BindView(R.id.eiv_password)
    EnterInfoView eiv_password;
    @BindView(R.id.btn_logout)
    Button btn_logout;
    private Context mContext;
    private Unbinder unbinder;
    private String mUsername;
    private String mPassword;
    private UserHelper mUserHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        SharedUtil shared = SharedUtil.getInstance(mContext);
        mUsername = shared.readShared("username", "");
        mPassword = shared.readShared("password", "");
        mUserHelper = new UserHelper();
    }

    @Override
    protected void initView() {
        eiv_password.initItem("密码:","请输入密码");
        eiv_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_logout})
     void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        String password = eiv_password.getContent();
        if (TextUtils.isEmpty(password)){
            showTips("密码不能为空");
            return;
        }
        if (!TextUtils.equals(password,mPassword)){
            showTips("密码错误");
            return;
        }
        //注销
        mUserHelper.deleteByUsername(mUsername);
        showTips("注销成功");
        Intent intent = new Intent(mContext, LoginRegisterActivity.class);
        startActivity(intent);
    }
}
