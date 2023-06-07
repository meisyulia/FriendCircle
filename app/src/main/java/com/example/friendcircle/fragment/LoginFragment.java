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

public class LoginFragment extends BaseFragment{

    @BindView(R.id.eiv_username)
    EnterInfoView eiv_username;
    @BindView(R.id.eiv_password)
    EnterInfoView eiv_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    private Context mContext;
    private Unbinder unbinder;
    private UserHelper mUserHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mUserHelper = new UserHelper();
    }

    @Override
    protected void initView() {
        eiv_username.initItem("用户名:","请输入用户名");
        eiv_password.initItem("密码:","请输入密码");
        eiv_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @OnClick(R.id.btn_login)
    public void loginClick(){
        String username = eiv_username.getContent();
        String password = eiv_password.getContent();
        User user = mUserHelper.selectByUsername(username);
        if (TextUtils.isEmpty(username)){
            showTips("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)){
            showTips("密码不能为空");
            return;
        }
        if (user==null){
            showTips("该用户名未注册！");
            return;
        }else{
            if (!TextUtils.equals(password,user.getPassword())){
                showTips("密码错误！");
                return;
            }
        }
        //写入共享
        SharedUtil shared = SharedUtil.getInstance(mContext);
        shared.writeShared("username",username);
        shared.writeShared("password",password);
        shared.writeShared("login_time", DateUtil.getNowDateTimeFormat());
        showTips("登陆成功");
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
