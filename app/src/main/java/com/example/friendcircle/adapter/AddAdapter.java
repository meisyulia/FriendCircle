package com.example.friendcircle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.R;
import com.example.friendcircle.bean.AddInfo;
import com.example.friendcircle.listener.RecyclerExtras;
import com.example.friendcircle.listener.RecyclerExtras.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAdapter extends RecyclerView.Adapter {

    private final Context context;

    private ArrayList<AddInfo> infoArray;
    private OnItemClickListener onItemClickListener;

    public AddAdapter(Context context, ArrayList<AddInfo> infoArray){
        this.context = context;
        this.infoArray = infoArray;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_icon_item, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddInfo item = infoArray.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        if (item!=null){
            if (position==infoArray.size()-1){
                itemHolder.v_line.setVisibility(View.GONE);
            }
            itemHolder.iv_icon.setImageResource(item.getResId());
            itemHolder.tv_title.setText(item.getTitle());
            itemHolder.rl_item.setOnClickListener(v->{
                if (onItemClickListener!=null){
                    onItemClickListener.OnItemClick(position, item.getTitle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (infoArray != null && infoArray.size()>0){
            return infoArray.size();
        }else{
            return 0;
        }

    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.v_line)
        View v_line;
        @BindView(R.id.rl_item)
        RelativeLayout rl_item;
        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
