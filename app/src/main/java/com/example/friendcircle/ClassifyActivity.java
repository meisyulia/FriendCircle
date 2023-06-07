package com.example.friendcircle;

import static com.example.friendcircle.fragment.PersonalFragment.REQUEST_CODE_CAMERA;
import static com.example.friendcircle.fragment.PersonalFragment.REQUEST_CODE_SELECT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendcircle.Constant.MyFunType;
import com.example.friendcircle.fragment.LogoutFragment;
import com.example.friendcircle.fragment.ModifyPassFragment;
import com.example.friendcircle.fragment.MyFunFragment;
import com.example.friendcircle.fragment.PersonalFragment;
import com.example.friendcircle.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifyActivity extends BaseActivity {

    private static final String TAG = "ClassifyActivity";
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.fcv_classify)
    FragmentContainerView fcv_classify;
    private MyFunType mSwitchPage;
    private String mSwitchTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        mSwitchPage = (MyFunType) getIntent().getSerializableExtra("switch_page");
        mSwitchTitle = getIntent().getStringExtra("switch_title");
        Log.i(TAG, "initData: switch_page="+mSwitchPage+",switch_title="+mSwitchTitle);
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(v->{
            onBackPressed();
        });
        switchPage(mSwitchPage);
    }



    public void switchPage(MyFunType page){
        switch (page){
            case PERSONAL:
                tv_head.setText("个人信息");
                getSupportFragmentManager().beginTransaction().replace(R.id.fcv_classify,new PersonalFragment(),
                        PersonalFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
            case MY_FUN:
                tv_head.setText(mSwitchTitle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fcv_classify,new MyFunFragment(),
                        MyFunFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
            case SETTING:
                tv_head.setText("设置");
                getSupportFragmentManager().beginTransaction().replace(R.id.fcv_classify,new SettingFragment(),
                        SettingFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
            case MODIFY_PASS:
                tv_head.setText("修改密码");
                getSupportFragmentManager().beginTransaction().replace(R.id.fcv_classify,new ModifyPassFragment(),
                        ModifyPassFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
            case LOGOUT:
                tv_head.setText("注销账号");
                getSupportFragmentManager().beginTransaction().replace(R.id.fcv_classify,new LogoutFragment(),
                        LogoutFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: onDestroy");
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: resultCode="+resultCode);
        if (requestCode==REQUEST_CODE_SELECT && resultCode == RESULT_OK && data != null){
            //传给fragment
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(PersonalFragment.class.getSimpleName());
            if (fragment!= null){
                fragment.onActivityResult(requestCode,resultCode,data);
            }
        }
        if (requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            //传给fragment
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(PersonalFragment.class.getSimpleName());
            if (fragment!= null){
                fragment.onActivityResult(requestCode,resultCode,data);
            }
        }
    }
}