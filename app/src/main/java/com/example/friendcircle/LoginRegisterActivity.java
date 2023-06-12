package com.example.friendcircle;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentContainerView;

import com.example.friendcircle.fragment.LoginFragment;
import com.example.friendcircle.fragment.RegisterFragment;
import com.example.friendcircle.util.PermissionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginRegisterActivity extends BaseActivity {

    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.fcv_login_register)
    FragmentContainerView fcv_login_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        checkAllPermission();
    }

    private void checkAllPermission() {
        boolean isPermission = PermissionUtil.checkMultiPermission(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 4096);
        if (!isPermission){
            Toast.makeText(this, "需要允许权限才能正常使用哦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        switchPage(PageType.LOGIN);
    }

    private void switchPage(PageType pageType){
        switch (pageType){
            case LOGIN:
                tv_login.setTextColor(getResources().getColor(R.color.main_color));
                tv_register.setTextColor(getResources().getColor(R.color.grey));
                tv_login.setTextSize(28);
                tv_register.setTextSize(24);
                getSupportFragmentManager().beginTransaction().replace(R.id.fcv_login_register,
                        new LoginFragment(),LoginFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
            case REGISTER:
                tv_register.setTextColor(getResources().getColor(R.color.main_color));
                tv_login.setTextColor(getResources().getColor(R.color.grey));
                tv_login.setTextSize(24);
                tv_register.setTextSize(28);
                getSupportFragmentManager().beginTransaction().replace(R.id.fcv_login_register,
                        new RegisterFragment(),RegisterFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                switchPage(PageType.LOGIN);
                break;
            case R.id.tv_register:
                switchPage(PageType.REGISTER);
                break;
        }
    }

    public enum PageType{
        LOGIN,
        REGISTER
    }


}