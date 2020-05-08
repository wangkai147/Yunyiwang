package com.wangk.mymusic.Home.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class HomeAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    List<String> titleList;

    public HomeAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> fragmentList,List<String> titleList) {
        super(fm, behavior);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public CharSequence getPageTitle(int position){
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}