package com.example.friendcircle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.R;
import com.example.friendcircle.adapter.FriendAdapter;
import com.example.friendcircle.bean.FriendInfo;
import com.example.friendcircle.database.FriendHelper;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.SpacesItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddFriendFragment extends BaseFragment{

    @BindView(R.id.rv_new_friend)
    RecyclerView rv_new_friend;
    @BindView(R.id.rv_people_nearby)
    RecyclerView rv_people_nearby;
    private Context mContext;
    private Unbinder unbinder;
    private String mUsername;
    private FriendHelper mFriHelper;
    private ArrayList<FriendInfo> mNearByList;
    private FriendAdapter mNearByAdapt;
    private ArrayList<FriendInfo> mAcceptList;
    private FriendAdapter mAccaptAdapt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_add_friend, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mFriHelper = new FriendHelper();
        mUsername = SharedUtil.getInstance(mContext).readShared("username", "");
        mNearByList = mFriHelper.getNearBy(mUsername);
        mAcceptList = mFriHelper.getAcceptFri(mUsername);
    }

    @Override
    protected void initView() {
        LinearLayoutManager nearByLLM = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_people_nearby.setLayoutManager(nearByLLM);
        mNearByAdapt = new FriendAdapter(mContext, mNearByList);
        rv_people_nearby.setAdapter(mNearByAdapt);
        rv_people_nearby.setItemAnimator(new DefaultItemAnimator());
        rv_people_nearby.addItemDecoration(new SpacesItemDecoration(1,R.color.black));
        LinearLayoutManager acceptLLM = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_new_friend.setLayoutManager(acceptLLM);
        mAccaptAdapt = new FriendAdapter(mContext, mAcceptList);
        rv_new_friend.setAdapter(mAccaptAdapt);
        rv_new_friend.setItemAnimator(new DefaultItemAnimator());
        rv_new_friend.addItemDecoration(new SpacesItemDecoration(1,R.color.black));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
