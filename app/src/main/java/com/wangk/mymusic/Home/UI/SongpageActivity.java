package com.wangk.mymusic.Home.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.Bean.Banners;
import com.wangk.mymusic.R;

public class SongpageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        Banners banners = (Banners) getIntent().getSerializableExtra("banner");

        Toast.makeText(this,"ddd"+banners.getPic(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_songpage;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
