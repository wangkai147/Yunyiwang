package com.wangk.mymusic.Login.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.HomeActivity;
import com.wangk.mymusic.R;

public class LoginActivity extends BaseActivity {
    private ImageView loginImageView;
    private Button loginButton;
    private Button intoButton;
    private EditText phoneEditText;
    private EditText passwordEditText;

    private String phone;
    private String password;

    private Bundle bundle;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        setListener();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }
    @Override
    protected void initView() {
        setTranslucent(this);
        loginImageView = bindView(R.id.loginImageView);
        loginButton = bindView(R.id.loginButton);
        intoButton = bindView(R.id.intoButton);
        phoneEditText = bindView(R.id.phoneEditText);
        passwordEditText = bindView(R.id.passwordEditText);
        Glide.with(this).load(R.drawable.login).into(loginImageView);
    }
    @Override
    protected void initData() {
        phone = phoneEditText.getText().toString();
        password = passwordEditText.getText().toString();
        bundle = new Bundle();
        bundle.putString("phone",phone);
        bundle.putString("password",password);

        intent = new Intent(LoginActivity.this, LoadingActivity.class);
        intent.putExtras(bundle);//放置数据
    }
    private void setListener(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this,"1", Toast.LENGTH_SHORT).show();
                initData();
                startActivity(intent);
            }
        });
        intoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
