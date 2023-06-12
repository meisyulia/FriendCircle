package com.example.friendcircle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.ClassifyActivity;
import com.example.friendcircle.Constant.MyExtraCons;
import com.example.friendcircle.Constant.MyFunType;
import com.example.friendcircle.R;
import com.example.friendcircle.adapter.FriCirAdapter;
import com.example.friendcircle.adapter.FriendAdapter;
import com.example.friendcircle.bean.FriendInfo;
import com.example.friendcircle.bean.SendCirInfo;
import com.example.friendcircle.database.FriendHelper;
import com.example.friendcircle.database.SendCircleHelper;
import com.example.friendcircle.listener.RecyclerExtras;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.SpacesItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFunFragment extends BaseFragment implements RecyclerExtras.OnItemClickCallBack {

    @BindView(R.id.rv_my_fun)
    RecyclerView rv_my_fun;
    private Context mContext;
    private Unbinder unbinder;
    private int mPosition;
    private String mUsername;
    private FriendHelper mFriHelper;
    private FriendAdapter mFriAdap;
    private SendCircleHelper mSendHelper;
    private FriCirAdapter mSendApt;
    private String mCirName;
    private ArrayList<SendCirInfo> mCirList;
    private ArrayList<FriendInfo> mFriList;

    public static MyFunFragment newInstance(int position,String cirName){
        MyFunFragment myFunFragment = new MyFunFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("cir_name",cirName);
        myFunFragment.setArguments(bundle);
        return myFunFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        if (getArguments()!=null){
            mPosition = getArguments().getInt("position");
            mCirName = getArguments().getString("cir_name");
        }
        View v = inflater.inflate(R.layout.fragment_my_fun, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mUsername = SharedUtil.getInstance(mContext).readShared("username", "");
        mFriHelper = new FriendHelper();
        mSendHelper = new SendCircleHelper();
        mFriList = mFriHelper.getFriend(mUsername);
    }

    @Override
    protected void initView() {
        switch (mPosition){
            case 0:
                showCircle(mCirName);
                break;
            case 1:
                showMyFriend();
                break;
        }
    }

    private void showMyFriend() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_my_fun.setLayoutManager(layoutManager);
        mFriAdap = new FriendAdapter(mContext, mFriList);
        rv_my_fun.setAdapter(mFriAdap);
        rv_my_fun.setItemAnimator(new DefaultItemAnimator());
        rv_my_fun.addItemDecoration(new SpacesItemDecoration(1,R.color.black));
        mFriAdap.setOnItemClickListener(this);
    }

    private void showCircle(String cirName) {
        if (TextUtils.isEmpty(cirName)){
            mCirList = mSendHelper.getCirByName(mUsername);
        }else{
            mCirList = mSendHelper.getCirByName(cirName);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_my_fun.setLayoutManager(layoutManager);
        mSendApt = new FriCirAdapter(mContext, mCirList);
        rv_my_fun.setAdapter(mSendApt);
        rv_my_fun.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnItemClick(int position) {
        FriendInfo friendInfo = mFriList.get(position);
        String cirName = friendInfo.getFriendName();
        String title = cirName+"的朋友圈";
        Intent intent = new Intent(mContext, ClassifyActivity.class);
        intent.putExtra("switch_page", MyFunType.MY_FUN);
        intent.putExtra("switch_title",title);
        intent.putExtra("cir_name",cirName);
        intent.putExtra("position", MyExtraCons.MY_CIRCLE);
        startActivity(intent);
    }
}
