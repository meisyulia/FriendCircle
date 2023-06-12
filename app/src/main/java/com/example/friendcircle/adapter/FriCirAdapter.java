package com.example.friendcircle.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.R;
import com.example.friendcircle.bean.SendCirInfo;
import com.example.friendcircle.util.GsonUtil;
import com.example.friendcircle.util.ImageUtil;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.CustomRoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriCirAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final String mUsername;
    private ArrayList<SendCirInfo> sendList;
    private OnDeleteItem onDeleteItem;
    private OnAvatarClick onAvatarClick;

    public FriCirAdapter(Context context, ArrayList<SendCirInfo> sendList){
        this.context = context;
        this.sendList = sendList;
        mUsername = SharedUtil.getInstance(context).readShared("username", "");
    }
    public void setDataList(ArrayList<SendCirInfo> sendList){
        this.sendList = sendList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_fri_cir_content, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SendCirInfo cirInfo = sendList.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        if (cirInfo!=null){
            if (TextUtils.isEmpty(cirInfo.getAvatar())){
                itemHolder.cusiv_avatar.setImageDrawable(context.getDrawable(R.drawable.rect_avatar));
            }else{
                ImageUtil.loadAvatarImage(context,cirInfo.getAvatar(),itemHolder.cusiv_avatar,R.drawable.rect_avatar);
            }
            itemHolder.tv_name.setText(cirInfo.getUsername());
            itemHolder.tv_text_content.setText(cirInfo.getContent());
            itemHolder.tv_update_time.setText(cirInfo.getSend_time());
            GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
            itemHolder.rv_pic_show.setLayoutManager(layoutManager);
            List<String> pathList = GsonUtil.parserJsonToArrayBeans(cirInfo.getPicList(), String.class);
            AddPicAdapter picAdap = new AddPicAdapter(context, (ArrayList<String>) pathList, 9);
            picAdap.OnlyShowPic(true);
            itemHolder.rv_pic_show.setAdapter(picAdap);
            if (TextUtils.equals(cirInfo.getUsername(),mUsername)){
                itemHolder.tv_delete.setVisibility(View.VISIBLE);
                itemHolder.tv_delete.setOnClickListener(v->{
                    if (onDeleteItem!=null){
                        onDeleteItem.onDeleteItem(position);
                    }
                });
            }else{
                itemHolder.tv_delete.setVisibility(View.GONE);
            }
            itemHolder.cusiv_avatar.setOnClickListener(v->{
                if (onAvatarClick!=null){
                    onAvatarClick.onAvatarClick(position);
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        if (sendList!=null&&sendList.size()>0){
            return sendList.size();
        }else{
            return 0;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cusiv_avatar)
        CustomRoundAngleImageView cusiv_avatar;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_text_content)
        TextView tv_text_content;
        @BindView(R.id.rv_pic_show)
        RecyclerView rv_pic_show;
        @BindView(R.id.tv_update_time)
        TextView tv_update_time;
        @BindView(R.id.tv_delete)
        TextView tv_delete;
        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
    public void setOnDeleteItemListener(OnDeleteItem onDeleteItem){
        this.onDeleteItem = onDeleteItem;
    }
    public void setOnAvatarClickListener(OnAvatarClick onAvatarClick){
        this.onAvatarClick = onAvatarClick;
    }
    public interface OnDeleteItem{
        void onDeleteItem(int position);
    }
    public interface OnAvatarClick{
        void onAvatarClick(int position);
    }
}
