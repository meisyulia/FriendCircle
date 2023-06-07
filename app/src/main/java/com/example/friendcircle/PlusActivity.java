package com.example.friendcircle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.friendcircle.Constant.PlusType;
import com.example.friendcircle.fragment.AddFriendFragment;
import com.example.friendcircle.fragment.BaseFragment;
import com.example.friendcircle.fragment.SendCircleFragment;

import java.io.Serializable;

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
}