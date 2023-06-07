package com.example.friendcircle.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.friendcircle.R;
import com.example.friendcircle.database.UserHelper;
import com.example.friendcircle.util.ClickUtil;
import com.example.friendcircle.util.ImagePickUtil;
import com.example.friendcircle.util.ImageUtil;
import com.example.friendcircle.util.SharedUtil;
import com.example.friendcircle.widget.ContentView;
import com.example.friendcircle.widget.CustomRoundAngleImageView;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;
//import com.example.image.ISNav;
//import com.example.image.model.config.ISCameraConfig;
//import com.example.image.model.config.ISListConfig;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;
/*import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;*/

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonalFragment extends BaseFragment implements PickPicDlgFragment.OnItemClickListener {

    private static final String TAG = "PersonalFragment";
    @BindView(R.id.tv_avatar)
    TextView tv_avatar;
    @BindView(R.id.iv_arrow_right)
    ImageView iv_arrow_right;
    @BindView(R.id.rl_avatar)
    RelativeLayout rl_avatar;
    @BindView(R.id.cv_username)
    ContentView cv_username;
    @BindView(R.id.cv_nickname)
    ContentView cv_nickname;
    @BindView(R.id.cv_signature)
    ContentView cv_signature;
    @BindView(R.id.cv_login_time)
    ContentView cv_login_time;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.cusiv_avatar)
    CustomRoundAngleImageView cusiv_avatar;
    private Context mContext;
    private Unbinder unbinder;
    private UserHelper mUserHelper;
    private SharedUtil mShared;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_CAMERA = 101;
    private String mAvatarUrl;
    /* private ArrayList<ImageItem> selImageList= new ArrayList<>(); //当前选择的所有图片
    ArrayList<ImageItem> images = null;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mShared = SharedUtil.getInstance(mContext);
        mUserHelper = new UserHelper();
        /*ImagePicker imagePicker = ImagePickUtil.initUpdateAvatar();*/
    }

    @Override
    protected void initView() {
        cv_username.initItem("账号",mShared.readShared("username",""));
        cv_nickname.initItem("昵称","~~~");
        cv_nickname.setEditable(true);
        cv_signature.initItem("个性签名","~~~~~~");
        cv_signature.setEditable(true);
        cv_login_time.initItem("登陆时间",mShared.readShared("login_time",""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_avatar, R.id.btn_save})
    void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.rl_avatar:
                updateAvatar(view);
                break;
            case R.id.btn_save:
                break;
        }
    }

    private void updateAvatar(View view) {
        if (!ClickUtil.isFastClick(view)){
            PickPicDlgFragment picDlgFragment = PickPicDlgFragment.newInstance();
            picDlgFragment.showNow(getActivity().getSupportFragmentManager(),PickPicDlgFragment.class.getSimpleName());
            picDlgFragment.setOnItemClickListener(this);
        }
    }

    @Override
    public void onSelectPhotos() {
        /*Intent intent = new Intent(mContext, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);*/
        ISListConfig config = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            config = new ISListConfig.Builder()
                    .getNowLan(true)
                    // 是否多选
                    .multiSelect(false)
                    .btnText("确定")
                    .btnTextColor(requireContext().getColor(R.color.black))
                    .statusBarColor(requireContext().getColor(R.color.white))
                    .rememberSelected(false)
                    .backResId(R.drawable.left_arrow_40)
                    .title("相册")
                    .titleColor(requireContext().getColor(R.color.black))
                    .titleBgColor(requireContext().getColor(R.color.white))
                    .allImagesText("所有图片")
                    .needCrop(true)
                    .cropSize(1, 1, 500, 500)
                    .compress()
                    .needCamera(false)
                    .maxNum(1)
                    .build();
        }
        ISNav.getInstance().toListActivity(requireContext(), config, REQUEST_CODE_SELECT );
    }

    @Override
    public void onOpenCamera() {
        /*Intent intent = new Intent(mContext, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);*/
        ISCameraConfig cameraConfig = new ISCameraConfig.Builder()
                .needCrop(false) // 裁剪
                .cropSize(1, 1, 500, 500)
                .build();
        ISNav.getInstance().toCameraActivity(requireContext(), cameraConfig, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "Frag onActivityResult: resultCode="+resultCode);
        /*if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    if (selImageList.size()>0){
                        ImageItem imageItem = selImageList.get(0);
                        Log.i(TAG, "onActivityResult: imageItem.path="+imageItem.path);
                    }

                    //adapter.setImages(selImageList);
                }
            }
        }*/
        if (requestCode==REQUEST_CODE_SELECT && resultCode == RESULT_OK && data != null){
            ArrayList<String> pathList = data.getStringArrayListExtra("result");
            if (pathList!=null){
                Log.i(TAG, "onActivityResult: pathList.size()="+pathList.size());
                mAvatarUrl = pathList.get(0);
                //Log.i(TAG, "onActivityResult: mAvatarUrl="+mAvatarUrl);
                ImageUtil.loadAvatarImage(mContext,mAvatarUrl,cusiv_avatar);
            }
        }
        if (requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            String path = data.getStringExtra("result");
            if (!TextUtils.isEmpty(path)){
                mAvatarUrl = path;
                //Log.i(TAG, "onActivityResult: mAvatarUrl="+mAvatarUrl);
                ImageUtil.loadAvatarImage(mContext,mAvatarUrl,cusiv_avatar);
            }
        }
    }


}
