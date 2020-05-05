package com.wangk.mymusic.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
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
import com.wangk.mymusic.R;

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

    private View view;


    private TabLayout homeTabLayout;
    private String[] titles = new String[]{"我的","推荐"};
    private List<String> titleList = new ArrayList<String>();


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
        //setColor(HomeActivity.this, Color.parseColor("#FFD33A32"));
        //setTranslucent(this);
/*        view = bindView(R.id.view);
        view.setBackgroundColor(Color.parseColor("#FFD33A32"));*/

        //获取TabLayout与ViewPager
        homeViewPager = bindView(R.id.homeViewPager);
        homeTabLayout = bindView(R.id.homeTabLayout);

        //创建Fragment
        myFragment = new MyFragment();
        recommendFragment = new RecommendFragment();
        //Fragment加入集合
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment);
        fragmentList.add(recommendFragment);


        //title加入集合
        for(int i=0;i<titles.length;i++){
            titleList.add(titles[i]);
        }

        fragmentManager = getSupportFragmentManager();
        homeAdapter = new HomeAdapter(fragmentManager,0,fragmentList,titleList);

        //TabLayout与ViewPager绑定
        homeTabLayout.setupWithViewPager(homeViewPager,false);

        homeViewPager.setAdapter(homeAdapter);

        //Viewpager的监听（这个监听是为TabLayout专门设计的）
        homeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeTabLayout));

        //TabLayout接受监听
        homeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
