package com.example.friendcircle;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.friendcircle.adapter.MainFragPagerAdapter;
import com.example.friendcircle.fragment.FriCirFragment;
import com.example.friendcircle.fragment.MyFragment;
import com.example.friendcircle.util.PermissionUtil;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.vp_main_contain)
    ViewPager2 vp_main_contain;
    @BindView(R.id.tv_fricir)
    TextView tv_fricir;
    @BindView(R.id.tv_my)
    TextView tv_my;
    private LinkedList<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        mFragmentList = new LinkedList<>();
        mFragmentList.add(new FriCirFragment());
        mFragmentList.add(new MyFragment());
        //检测所有权限
        checkAllPermission();

    }

    private void checkAllPermission() {
        boolean isPermission = PermissionUtil.checkMultiPermission(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 4096);
        if (!isPermission){
            Toast.makeText(this, "需要允许权限才能正常使用哦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*int switch_page = getIntent().getIntExtra("switch_page", 0);
        changePager(switch_page);
        vp_main_contain.setCurrentItem(switch_page);*/
    }

    @Override
    protected void initView() {
        MainFragPagerAdapter mainFragPagerAdapter = new MainFragPagerAdapter(getSupportFragmentManager(), getLifecycle(), mFragmentList);
        vp_main_contain.setAdapter(mainFragPagerAdapter);
        vp_main_contain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changePager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changePager(int position) {
        switch (position){
            case 0:
                tv_fricir.setSelected(true);
                tv_my.setSelected(false);
                break;
            case 1:
                tv_fricir.setSelected(false);
                tv_my.setSelected(true);
                break;
        }
    }

    @OnClick({R.id.tv_fricir, R.id.tv_my})
    void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fricir:
                changePager(0);
                vp_main_contain.setCurrentItem(0);
                break;
            case R.id.tv_my:
                changePager(1);
                vp_main_contain.setCurrentItem(1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: Main");
        super.onDestroy();
    }
}