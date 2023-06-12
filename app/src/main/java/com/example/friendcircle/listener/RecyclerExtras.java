package com.example.friendcircle.listener;

public class RecyclerExtras {
    public interface OnItemClickListener{
        void OnItemClick(int position,String title);
    }
    public interface OnItemClickCallBack{
        void OnItemClick(int position);
    }
}
