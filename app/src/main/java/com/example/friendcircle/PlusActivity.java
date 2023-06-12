package com.example.friendcircle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.friendcircle.Constant.Constants;
import com.example.friendcircle.Constant.PlusType;
import com.example.friendcircle.fragment.AddFriendFragment;
import com.example.friendcircle.fragment.SendCircleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlusActivity extends BaseActivity {

    private static final String TAG = "PlusActivity";
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.fcv_plus)
    FragmentContainerView fcv_plus;
    private PlusType mSwitchPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        mSwitchPage = (PlusType) getIntent().getSerializableExtra("switch_page");
        Log.i(TAG, "initData: switch_page="+mSwitchPage);
    }

    @Override
    protected void initView() {
        switchPage(mSwitchPage);
        iv_back.setOnClickListener(v->{
            onBackPressed();
            //finish();
        });

    }

    private void switchPage(PlusType type){
        switch (type){
            case ADD_FRIEND:
                tv_head.setText("添加好友");
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fcv_plus,new AddFriendFragment(),AddFriendFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
            case SEND_CIRCLE:
                tv_head.setText("发朋友圈");
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fcv_plus,new SendCircleFragment(),SendCircleFragment.class.getSimpleName()).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: resultCode="+resultCode);
        if (requestCode== Constants.REQUEST_CODE_SELECT && resultCode == RESULT_OK && data != null){
            //传给fragment
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SendCircleFragment.class.getSimpleName());
            if (fragment!= null){
                fragment.onActivityResult(requestCode,resultCode,data);
            }
        }
        if (requestCode==Constants.REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            //传给fragment
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SendCircleFragment.class.getSimpleName());
            if (fragment!= null){
                fragment.onActivityResult(requestCode,resultCode,data);
            }
        }
    }
}