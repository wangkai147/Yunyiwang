package com.wangk.mymusic.Home;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.R;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setTranslucent(this);
    }

    @Override
    protected void initData() {

    }
}
