package com.example.friendcircle.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.friendcircle.R;
import com.example.friendcircle.listener.OnDlgItemClickListener;
import com.example.friendcircle.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PickPicDlgFragment extends DialogFragment {


    private static final String TAG = "PickPicDlgFragment";
    private static PickPicDlgFragment pickPicDlgFragment;
    @BindView(R.id.tv_select_photos)
    TextView tv_select_photos;
    @BindView(R.id.tv_camera)
    TextView tv_camera;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    private Context mContext;
    private Unbinder unbinder;
    private OnDlgItemClickListener onItemClickListener;

    public static PickPicDlgFragment newInstance(){
        if (pickPicDlgFragment != null && pickPicDlgFragment.isResumed()){
            return null;
        }
        pickPicDlgFragment = new PickPicDlgFragment();
        pickPicDlgFragment.setCancelable(false);
        return pickPicDlgFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View v = inflater.inflate(R.layout.dialog_picker_picture, container, false);
        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        initWindow();
    }

    private void initWindow() {
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout((int) (Utils.getScreenWidth(mContext)*0.9),ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_select_photos, R.id.tv_camera, R.id.tv_cancel})
    void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_photos:
                if (onItemClickListener!=null){
                    onItemClickListener.onSelectPhotos();
                }
                dismiss();
                break;
            case R.id.tv_camera:
                if (onItemClickListener!=null){
                    onItemClickListener.onOpenCamera();
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }



    public void setOnItemClickListener(OnDlgItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


}
