package com.example.friendcircle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.R;
import com.example.friendcircle.bean.FriendInfo;
import com.example.friendcircle.database.FriendHelper;
import com.example.friendcircle.listener.RecyclerExtras;
import com.example.friendcircle.model.FriRelation;
import com.example.friendcircle.util.DateUtil;
import com.example.friendcircle.util.ImageUtil;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.CustomRoundAngleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendAdapter extends RecyclerView.Adapter {

    private static final String TAG = "FriendAdapter";
    private final Context context;
    private final long mId;
    private final String mUsername;

    private ArrayList<FriendInfo> infoList;
    private FriendHelper mHelper = new FriendHelper();
    private AdapterView.OnItemClickListener onItemClickListener;
    private RecyclerExtras.OnItemClickCallBack onItemClickCallBack;


    public FriendAdapter(Context context, ArrayList<FriendInfo> infoList){
        this.context = context;
        this.infoList = infoList;
        mId = SharedUtil.getInstance(context).readShared("id", 0);
        mUsername = SharedUtil.getInstance(context).readShared("username", "");
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_firend, parent,false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FriendInfo item = infoList.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        if (item!=null){
            ImageUtil.loadAvatarImage(context,item.getAvatar(),itemHolder.caiv_avatar,R.drawable.rect_avatar);
            itemHolder.tv_name.setText(item.getFriendName());
            showOperate(itemHolder,item,position);
            if (item.getRelation()==2){
                itemHolder.tv_time.setText(item.getApplication_time());
            }else if (item.getRelation()==3){
                itemHolder.tv_time.setText(item.getAcceptor_time());
            }
        }
    }

    private void showOperate(ItemHolder itemHolder,FriendInfo item,int position) {
        itemHolder.tv_operate.setVisibility(View.VISIBLE);
        itemHolder.iv_right.setVisibility(View.GONE);
        switch (item.getRelation()){
            case 0:
                itemHolder.tv_operate.setText("添加好友");
                itemHolder.tv_operate.setBackgroundColor(context.getColor(R.color.main_color));
                itemHolder.tv_operate.setOnClickListener(v->{
                    //新增关系：
                    FriRelation friRelation = new FriRelation();
                    friRelation.setApplicant_id(mId);
                    friRelation.setAcceptor_id(item.getFriend_id());
                    friRelation.setApplication_time(DateUtil.getNowDateTimeFormat());
                    friRelation.setRelation_status(0);
                    friRelation.save();
                    infoList = mHelper.getNearBy(mUsername);
                    notifyDataSetChanged();
                });
                break;
            case 1:
                itemHolder.tv_operate.setText("已申请");
                itemHolder.tv_operate.setBackgroundColor(context.getColor(R.color.bg_3c3f41));
                break;
            case 2:
                itemHolder.tv_operate.setText("接受");
                itemHolder.tv_operate.setBackgroundColor(context.getColor(R.color.main_color));
                //修改关系
                itemHolder.tv_operate.setOnClickListener(v->{
                    mHelper.updateFriRel(item.getFriend_id(),mId,1,DateUtil.getNowDateTimeFormat());
                    infoList = mHelper.getAcceptFri(mUsername);
                    notifyDataSetChanged();
                });
                break;
            case 3:
                itemHolder.tv_operate.setVisibility(View.GONE);
                itemHolder.iv_right.setVisibility(View.VISIBLE);
                itemHolder.rl_item.setOnClickListener(v->{
                    if (onItemClickCallBack!=null){
                        onItemClickCallBack.OnItemClick(position);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (infoList!=null && infoList.size()>0){
            return infoList.size();
        }else{
            return 0;
        }
    }
    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.caiv_avatar)
        CustomRoundAngleImageView caiv_avatar;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_operate)
        TextView tv_operate;
        @BindView(R.id.iv_right)
        ImageView iv_right;
        @BindView(R.id.rl_item)
        RelativeLayout rl_item;
        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public void setOnItemClickListener(RecyclerExtras.OnItemClickCallBack onItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack;
    }
}
