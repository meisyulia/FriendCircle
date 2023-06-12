package com.example.friendcircle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.ClassifyActivity;
import com.example.friendcircle.Constant.MyFunType;
import com.example.friendcircle.R;
import com.example.friendcircle.adapter.ClassifyAdapter;
import com.example.friendcircle.database.UserHelper;
import com.example.friendcircle.model.User;
import com.example.friendcircle.util.ImageUtil;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.CustomRoundAngleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment{

    private static final String TAG = "MyFragment";
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_signature)
    TextView tv_signature;
    @BindView(R.id.rv_classify)
    RecyclerView rv_classify;
    @BindView(R.id.tv_setting)
    TextView tv_setting;
    @BindView(R.id.rl_my_info)
    RelativeLayout rl_my_info;
    @BindView(R.id.cusiv_avatar)
    CustomRoundAngleImageView cusiv_avatar;
    private Context mContext;
    private Unbinder unbinder;
    private ArrayList<String> mClassify;
    private SharedUtil mShared;
    private String mUsername;
    private UserHelper mUserHelper;
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mClassify = new ArrayList<>();
        mClassify.add("我的朋友圈");
        mClassify.add("我的好友");
        mShared = SharedUtil.getInstance(mContext);
        mUsername = mShared.readShared("username", "");
        mUserHelper = new UserHelper();

    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_classify.setLayoutManager(layoutManager);
        ClassifyAdapter classifyAdapter = new ClassifyAdapter(mContext, mClassify);
        rv_classify.setAdapter(classifyAdapter);
        rv_classify.setItemAnimator(new DefaultItemAnimator());
        classifyAdapter.setOnItemClickListener(((position, title) -> {
            //跳转到对应页面
            Intent intent = new Intent(getActivity(), ClassifyActivity.class);
            intent.putExtra("switch_page", MyFunType.MY_FUN);
            intent.putExtra("switch_title",title);
            intent.putExtra("position",position);
            startActivity(intent);
        }));
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: MyFra--resume");
        super.onResume();
        mUser = mUserHelper.selectByUsername(mUsername);
        ImageUtil.loadAvatarImage(mContext,mUser.getAvatar(),cusiv_avatar,R.drawable.rect_avatar);
        tv_nickname.setText(TextUtils.isEmpty(mUser.getNickname())?"~~~":mUser.getNickname());
        tv_signature.setText(TextUtils.isEmpty(mUser.getSignature())?"~~~~~~":mUser.getSignature());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({ R.id.tv_setting, R.id.rl_my_info})
    void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.rl_my_info:
                toOtherPage(ClassifyActivity.class,MyFunType.PERSONAL);
                break;
            case R.id.tv_setting:
                toOtherPage(ClassifyActivity.class,MyFunType.SETTING);
                break;
        }
    }

    private void toOtherPage(Class<?> cls, MyFunType type) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtra("switch_page",type);
        startActivity(intent);
    }
}
