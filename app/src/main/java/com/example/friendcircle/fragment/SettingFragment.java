package com.example.friendcircle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.ClassifyActivity;
import com.example.friendcircle.Constant.MyFunType;
import com.example.friendcircle.LoginRegisterActivity;
import com.example.friendcircle.R;
import com.example.friendcircle.adapter.SettingAdapter;
import com.example.friendcircle.util.VersionUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingFragment extends BaseFragment{

    @BindView(R.id.rv_setting_list)
    RecyclerView rv_setting_list;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.btn_quit)
    Button btn_quit;
    private Context mContext;
    private Unbinder unbinder;
    private SettingAdapter mSettingAdapter;
    private String[] mStringFun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mStringFun = new String[]{"修改密码","账号注销"};
        mSettingAdapter = new SettingAdapter(mContext, mStringFun);
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_setting_list.setLayoutManager(layoutManager);
        rv_setting_list.setAdapter(mSettingAdapter);
        mSettingAdapter.setOnItemClickListener(((position, title) -> {
            Intent intent = new Intent(getActivity(), ClassifyActivity.class);
            if (position==0){
                intent.putExtra("switch_page", MyFunType.MODIFY_PASS);
            }else if (position==1){
                intent.putExtra("switch_page",MyFunType.LOGOUT);
            }
            startActivity(intent);
        }));
        tv_version.setText("V"+VersionUtil.getVersionName(mContext));
        btn_quit.setOnClickListener(v->{
            Intent intent = new Intent(mContext, LoginRegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
