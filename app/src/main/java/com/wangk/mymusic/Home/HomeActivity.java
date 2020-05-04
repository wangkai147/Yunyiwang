package com.wangk.mymusic.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.Adapter.HomeAdapter;
import com.wangk.mymusic.Home.Fragment.MyFragment;
import com.wangk.mymusic.Home.Fragment.RecommendFragment;
import com.wangk.mymusic.Login.Bean.Login;
import com.wangk.mymusic.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {


    private ViewPager homeViewPager;

    //适配器
    private HomeAdapter homeAdapter;

    //Fragment
    private Fragment myFragment;
    private Fragment recommendFragment;
    //存放Fragment的集合
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;


    private TabLayout homeTabLayout;
    private String[] titles = new String[]{"推荐","我的"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initData();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setColor(HomeActivity.this, Color.parseColor("#FFD33A32"));
        //setTranslucent(this);

        homeViewPager = findViewById(R.id.homeViewPager);
        myFragment = new MyFragment();
        recommendFragment = new RecommendFragment();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment);
        fragmentList.add(recommendFragment);


        homeTabLayout = bindView(R.id.homeTabLayout);
        for(int i=0;i<titles.length;i++){
            homeTabLayout.addTab(homeTabLayout.newTab());
            homeTabLayout.getTabAt(i).setText(titles[i]);
        }
        homeTabLayout.setupWithViewPager(homeViewPager,false);



        fragmentManager = getSupportFragmentManager();

        homeAdapter = new HomeAdapter(fragmentManager,0,fragmentList);

        homeViewPager.setAdapter(homeAdapter);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        if(result!=null){
            Bundle bundle = new Bundle();
            bundle.putString("result",result);//这里的titles就是我们要传的值
            myFragment.setArguments(bundle);
            recommendFragment.setArguments(bundle);
        }
    }
}
