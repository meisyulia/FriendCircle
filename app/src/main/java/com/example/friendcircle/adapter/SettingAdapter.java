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

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final String[] strings;
    private OnItemClickListener onItemClickListener;

    public SettingAdapter(Context context, String[] strings){
        this.context = context;
        this.strings = strings;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String title = strings[position];
        ItemHolder itemHolder = (ItemHolder) holder;
        if (!TextUtils.isEmpty(title)){
            itemHolder.tv_title.setText(title);
            itemHolder.ll_item.setOnClickListener(v->{
                if (onItemClickListener!=null){
                    onItemClickListener.OnItemClick(position,title);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (strings!=null && strings.length>0){
            return strings.length;
        }else{
            return 0;
        }

    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.ll_item)
        LinearLayout ll_item;
        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
