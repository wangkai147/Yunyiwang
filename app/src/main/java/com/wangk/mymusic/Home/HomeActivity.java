package com.wangk.mymusic.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Login.Bean.Login;
import com.wangk.mymusic.Login.UI.LoadingActivity;
import com.wangk.mymusic.R;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initData();
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
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        if(result!=null){
            Toast.makeText(HomeActivity.this, result, Toast.LENGTH_SHORT).show();
            //解析json
            Gson gson = new Gson();
            //login存储登录数据
            Login login = gson.fromJson(result,Login.class);
        }else{
            Toast.makeText(HomeActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
