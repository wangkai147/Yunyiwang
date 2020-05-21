package com.wangk.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Login.UI.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity {

    private ImageView mainImage;
    private int taskTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        initView();
        initData();
        //Glide为开屏页加载动图
        Glide.with(this).load(R.drawable.mainbackground).into(mainImage);

        //3秒后跳转到HomeActivity
        new Timer().schedule(new TimerTask(){
            public void run(){
                //TODO  todo something here
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                //跳转后销毁activity
                finish();
            }
        }, taskTime);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    //初始化View and Date
    @Override
    protected void initView(){
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainImage = findViewById(R.id.mainImage);
    }

    @Override
    protected void initData() {
        taskTime = 3000;
    }
}
