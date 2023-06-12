package com.example.friendcircle.fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.friendcircle.Constant.Constants.REQUEST_CODE_CAMERA;
import static com.example.friendcircle.Constant.Constants.REQUEST_CODE_SELECT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.R;
import com.example.friendcircle.adapter.AddPicAdapter;
import com.example.friendcircle.model.SendCircle;
import com.example.friendcircle.util.DateUtil;
import com.example.friendcircle.util.GsonUtil;
import com.example.friendcircle.util.SharedUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SendCircleFragment extends BaseFragment{

    private static final String TAG = "SendCircleFragment";
    @BindView(R.id.et_send_text)
    EditText et_send_text;
    @BindView(R.id.rv_add_pic)
    RecyclerView rv_add_pic;
    @BindView(R.id.btn_send)
    Button btn_send;
    private Context mContext;
    private Unbinder unbinder;
    private int mMaxCount = 9;
    private AddPicAdapter mAddPicAdapt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_send_circle, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mAddPicAdapt = new AddPicAdapter(mContext, new ArrayList<>(), mMaxCount);

    }

    @Override
    protected void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4 );
        rv_add_pic.setLayoutManager(layoutManager);
        rv_add_pic.setAdapter(mAddPicAdapt);
        rv_add_pic.setItemAnimator(new DefaultItemAnimator());
        btn_send.setOnClickListener(v->sendCircle());
    }

    private void sendCircle() {
        String username = SharedUtil.getInstance(mContext).readShared("username", "");
        String content = et_send_text.getText().toString();
        ArrayList<String> pathList = mAddPicAdapt.getPathList();
        String picList = GsonUtil.ListToJson(pathList);
        String send_time = DateUtil.getNowDateTimeFormat();
        SendCircle sendCircle = new SendCircle();
        sendCircle.setUsername(username);
        sendCircle.setContent(content);
        sendCircle.setPicList(picList);
        sendCircle.setSend_time(send_time);
        sendCircle.save();
        showTips("发送成功");
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_SELECT && resultCode == RESULT_OK && data != null){
            ArrayList<String> pathList = data.getStringArrayListExtra("result");
            if (pathList!=null){
                Log.i(TAG, "onActivityResult: pathList.size()="+pathList.size());
                mAddPicAdapt.setPathList(pathList);
            }
        }
        if (requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            String path = data.getStringExtra("result");
            if (!TextUtils.isEmpty(path)){
                mAddPicAdapt.addPathUrl(path);
            }
        }
    }
}
