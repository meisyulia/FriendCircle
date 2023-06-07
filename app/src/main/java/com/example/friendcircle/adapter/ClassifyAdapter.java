package com.example.friendcircle.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.R;
import com.example.friendcircle.listener.RecyclerExtras;
import com.example.friendcircle.listener.RecyclerExtras.OnItemClickListener;
import com.example.friendcircle.util.ClickUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifyAdapter extends RecyclerView.Adapter {

    private final Context context;

    private ArrayList<String> itemList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ClassifyAdapter(Context context, ArrayList<String> itemList){
        this.context = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_classify, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String title = itemList.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        if (!TextUtils.isEmpty(title)){
            itemHolder.tv_title.setText(title);
            itemHolder.ll_item.setOnClickListener(v->{
                if (onItemClickListener!=null){
                    if (!ClickUtil.isFastClick(itemHolder.ll_item)){
                        onItemClickListener.OnItemClick(position,title);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (itemList != null && itemList.size()>0){
            return itemList.size();
        }else {
            return 0;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_item)
        LinearLayout ll_item;
        @BindView(R.id.tv_title)
        TextView tv_title;
        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


}
