package com.example.friendcircle.adapter;

import static com.example.friendcircle.Constant.Constants.REQUEST_CODE_CAMERA;
import static com.example.friendcircle.Constant.Constants.REQUEST_CODE_SELECT;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendcircle.R;
import com.example.friendcircle.fragment.PickPicDlgFragment;
import com.example.friendcircle.listener.OnDlgItemClickListener;
import com.example.friendcircle.util.ClickUtil;
import com.example.friendcircle.util.ImageUtil;
import com.example.friendcircle.widget.AddedPicView;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPicAdapter extends RecyclerView.Adapter implements OnDlgItemClickListener, AddedPicView.OnPicDeleteListener, AddedPicView.OnPicClickListener {

    private static final String TAG = "AddPicAdapter";
    private final Context context;
    private final ArrayList<String> pathList;
    private final int maxCount;
    private boolean isOnlyShow;


    public AddPicAdapter(Context context, ArrayList<String> pathList, int maxCount){
        this.context = context;
        this.pathList = pathList;
        this.maxCount = maxCount;
        isOnlyShow = false;
    }
    public void OnlyShowPic(boolean isOnlyShow){
        this.isOnlyShow = isOnlyShow;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_add_pic, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        if (isOnlyShow){
            String picPath = pathList.get(position);
            showView(itemHolder,ImageType.ADDED_PIC);
            itemHolder.apv_pic.initPic(picPath,position);
            itemHolder.apv_pic.setIconVisible(View.GONE);
            itemHolder.apv_pic.setOnPicClickListener(this);
        }else{
            if(pathList.size()<=0 || position==pathList.size()){
                showView(itemHolder,ImageType.ADD_ICON);
                itemHolder.iv_add_picture.setOnClickListener(v->{
                    addPic(v);
                });
            }else{
                String picPath = pathList.get(position);
                showView(itemHolder,ImageType.ADDED_PIC);
                itemHolder.apv_pic.initPic(picPath,position);
                itemHolder.apv_pic.setOnPicDeleteListener(this);
                itemHolder.apv_pic.setOnPicClickListener(this);
            }
        }

    }

    private void addPic(View view) {
        if (!ClickUtil.isFastClick(view)){
            PickPicDlgFragment picDlgFragment = PickPicDlgFragment.newInstance();
            FragmentActivity fragmentActivity = (FragmentActivity) context;
            picDlgFragment.showNow(fragmentActivity.getSupportFragmentManager(),PickPicDlgFragment.class.getSimpleName());
            picDlgFragment.setOnItemClickListener(this);
        }
    }

    private void showView(ItemHolder itemHolder,ImageType type){
        switch (type){
            case ADD_ICON:
                itemHolder.iv_add_picture.setVisibility(View.VISIBLE);
                itemHolder.apv_pic.setVisibility(View.GONE);
                break;
            case ADDED_PIC:
                itemHolder.iv_add_picture.setVisibility(View.GONE);
                itemHolder.apv_pic.setVisibility(View.VISIBLE);
                break;
        }
    }



    @Override
    public int getItemCount() {
        if (isOnlyShow){
            if (pathList!=null && pathList.size()>0){
                return pathList.size();
            }else{
                return 0;
            }
        }else{
            if (pathList!=null && pathList.size()>0){
                return pathList.size()<maxCount?pathList.size()+1:maxCount;
            }else {
                return maxCount>0?1:0;
            }
        }


    }

    @Override
    public void onSelectPhotos() {
        int size = (pathList!=null)?pathList.size():0;
        Log.i(TAG, "onSelectPhotos: maxNum="+(maxCount-size));
        ISListConfig config = null;
        config = new ISListConfig.Builder()
                .getNowLan(true)
                // 是否多选
                .multiSelect(true)
                .btnText("确定")
                .btnTextColor(context.getColor(R.color.black))
                .statusBarColor(context.getColor(R.color.white))
                .rememberSelected(false)
                .backResId(R.drawable.left_arrow_40)
                .title("相册")
                .titleColor(context.getColor(R.color.black))
                .titleBgColor(context.getColor(R.color.white))
                .allImagesText("所有图片")
                .needCrop(true)
                .cropSize(1, 1, 500, 500)
                .compress()
                .needCamera(false)
                .maxNum(maxCount-size)
                .build();
        ISNav.getInstance().toListActivity(context, config, REQUEST_CODE_SELECT );
    }

    @Override
    public void onOpenCamera() {
        ISCameraConfig cameraConfig = new ISCameraConfig.Builder()
                .needCrop(true) // 裁剪
                .cropSize(1, 1, 500, 500)
                .build();
        ISNav.getInstance().toCameraActivity(context, cameraConfig, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onPicDelete(int position) {
        pathList.remove(position);
        notifyItemRemoved(position);
        //虽然页面显示他们已经被移除了，但是对应的position没有根据列表进行更新，需要更新下标
        notifyItemRangeChanged(position,pathList.size()-position);
    }

    public void setPathList(ArrayList<String> pathLists){
        pathList.addAll(pathLists);
        notifyDataSetChanged();
    }

    public ArrayList<String> getPathList(){
        return pathList;
    }

    public void addPathUrl(String pathUrl){
        pathList.add(pathUrl);
        notifyDataSetChanged();
    }

    @Override
    public void onPicClick(int position) {
        Log.i(TAG, "onPicClick: path="+pathList.get(position));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(layoutParams);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(context.getColor(R.color.white));


        ImageView imageView = new ImageView(context);
        ImageUtil.loadImage(context,pathList.get(position),imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        layout.addView(imageView);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        /*// 设置对话框宽度为屏幕宽度的80%
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (Utils.getScreenWidth(context)*0.7);
        dialog.getWindow().setAttributes(lp);
        // 设置对话框背景为透明
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
        layout.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.apv_pic)
        AddedPicView apv_pic;
        @BindView(R.id.iv_add_picture)
        ImageView iv_add_picture;
        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    private enum ImageType{
        ADD_ICON,
        ADDED_PIC
    }
}
