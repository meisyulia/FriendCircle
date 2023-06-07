package com.example.friendcircle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.friendcircle.MainActivity;
import com.example.friendcircle.R;
import com.example.friendcircle.database.UserHelper;
import com.example.friendcircle.model.User;
import com.example.friendcircle.util.DateUtil;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.EnterInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFragment extends BaseFragment{

    private static final String TAG = "RegisterFragment";
    @BindView(R.id.eiv_username)
    EnterInfoView eiv_username;
    @BindView(R.id.eiv_password)
    EnterInfoView eiv_password;
    @BindView(R.id.eiv_confirm_pass)
    EnterInfoView eiv_confirm_pass;
    @BindView(R.id.btn_register)
    Button btn_register;
    private Context mContext;
    private Unbinder unbinder;
    private UserHelper mUserHpler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mUserHpler = new UserHelper();
    }

    @Override
    protected void initView() {
        eiv_username.initItem("用户名:","请输入用户名");
        eiv_password.initItem("密码:","请输入密码");
        eiv_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        eiv_confirm_pass.initItem("确认密码:","请再次输入密码");
        eiv_confirm_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_register})
    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                clickRegister();
                break;
        }
    }

    private void clickRegister() {
        String username = eiv_username.getContent();
        String password = eiv_password.getContent();
        String confirmPass = eiv_confirm_pass.getContent();
        if (TextUtils.isEmpty(username)){
            showTips("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)){
            showTips("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(confirmPass)){
            showTips("确认密码不能为空");
            return;
        }
        Log.i(TAG, "clickRegister: selectUser="+mUserHpler.selectByUsername(username));
        if (mUserHpler.selectByUsername(username)!=null){
            showTips("该用户名已注册！");
            return;
        }
        if (!TextUtils.equals(password,confirmPass)){
            showTips("密码和确认密码不一致！");
            return;
        }
        //写入数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.save();
        showTips("注册成功");
    }
}
