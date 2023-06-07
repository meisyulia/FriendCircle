package com.example.friendcircle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.friendcircle.R;
import com.example.friendcircle.database.UserHelper;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.EnterInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ModifyPassFragment extends BaseFragment{

    @BindView(R.id.eiv_old_pass)
    EnterInfoView eiv_old_pass;
    @BindView(R.id.eiv_new_pass)
    EnterInfoView eiv_new_pass;
    @BindView(R.id.eiv_confirm_pass)
    EnterInfoView eiv_confirm_pass;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    private Context mContext;
    private Unbinder unbinder;
    private SharedUtil mShared;
    private UserHelper mUserHelper;
    private String mUsername;
    private String mPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_modify_pass, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mShared = SharedUtil.getInstance(mContext);
        mUsername = mShared.readShared("username", "");
        mPassword = mShared.readShared("password", "");
        mUserHelper = new UserHelper();
    }

    @Override
    protected void initView() {
        eiv_old_pass.initItem("原密码:","请输入原密码");
        eiv_old_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        eiv_new_pass.initItem("新密码:","请输入新密码");
        eiv_new_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        eiv_confirm_pass.initItem("确认密码:","请再次输入新密码");
        eiv_confirm_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_confirm})
    void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                updatePassword();
                break;
        }
    }

    private void updatePassword() {
        String oldPass = eiv_old_pass.getContent();
        String newPass = eiv_new_pass.getContent();
        String confirmPass = eiv_confirm_pass.getContent();
        if (TextUtils.isEmpty(oldPass)){
            showTips("原密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(newPass)){
            showTips("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(confirmPass)){
            showTips("确认密码不能为空");
            return;
        }
        if (!TextUtils.equals(oldPass,mPassword)){
            showTips("原密码错误");
            return;
        }
        if (TextUtils.equals(oldPass,newPass)){
            showTips("新密码与原密码相同");
            return;
        }
        if (!TextUtils.equals(newPass,confirmPass)){
            showTips("密码和确认密码不一致");
            return;
        }
        //修改
        mShared.writeShared("password",newPass);
        mUserHelper.updateUser(mUsername,newPass);
        showTips("修改成功");
    }

}
