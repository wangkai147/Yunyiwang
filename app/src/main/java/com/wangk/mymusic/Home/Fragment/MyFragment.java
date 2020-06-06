package com.wangk.mymusic.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.wangk.mymusic.Home.Bean.SongRoot;
import com.wangk.mymusic.Home.Fragment.Adapter.CoverAdapter;
import com.wangk.mymusic.Home.Fragment.CoverBean.CoverRootBean;
import com.wangk.mymusic.Home.Fragment.CoverBean.Playlist;
import com.wangk.mymusic.Home.UI.CoverActivity;
import com.wangk.mymusic.Login.Bean.Login;
import com.wangk.mymusic.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyFragment extends Fragment {

    private String result = null;
    private Login login = null;

    private View rootView;
    private Context mContext;

    private NestedScrollView myFragmentNestedScrollView;
    private ImageView userBackgroundImage;
    private ImageView userIcon;


    private List<Playlist> mData = null;
    private CoverAdapter mAdapter = null;
    private ListView coverListView;

    private OkHttpClient client = new OkHttpClient();


/*
    //activity的布局
    private ConstraintLayout barBackground;
*/

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();

        Bundle bundle = getArguments();
        if(bundle!=null){
            result = bundle.getString("result");

            //解析json
            Gson gson = new Gson();
            //login存储登录数据
            login = gson.fromJson(result,Login.class);
        }
    }

    //网络请求获取用户详情
    private class AsyncTaskRefresh extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            //添加线程任务(网络请求)
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("uid",login.getAccount().getId()+"")
                        .build();

                //推荐歌单
                Request request = new Request.Builder()
                        .url("https://vast-coast-94601.herokuapp.com/user/detail")
                        .post(formBody)
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
            //可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result!=null){
                Toast.makeText(getActivity(),"获取用户详情成功",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"获取用户详情失败",Toast.LENGTH_SHORT).show();
            }
        }
        //获取用户信息，歌单，收藏 mv dj数量

        //更新用户信息

        //获取用户歌单

        //更新歌单

        //更新歌单描述

        //更新歌单名

        //更新歌单标签

        //获取用户动态

        //获取热门话题
    }

    //网络请求获取用户详情
    private class AsyncTaskRefresh1 extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            //添加线程任务(网络请求)
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("uid",login.getAccount().getId()+"")
                        .build();

                //推荐歌单
                Request request = new Request.Builder()
                        .url("https://vast-coast-94601.herokuapp.com/user/playlist")
                        .post(formBody)
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
            //可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result!=null){
                //解析json
                Gson gson = new Gson();
                CoverRootBean coverRootBean = gson.fromJson(result, CoverRootBean.class);
                mData = coverRootBean.getPlaylist();

                //网络请求获取数据
                mAdapter = new CoverAdapter((ArrayList<Playlist>) mData, mContext);
                coverListView.setAdapter(mAdapter);

                Toast.makeText(getActivity(),"获取playList成功",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"获取playList失败",Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my, container, false);
        initView(rootView);
        if(login!=null) {
            Toast.makeText(mContext,"id"+login.getAccount().getId(),Toast.LENGTH_SHORT).show();

            Executor exec = new ThreadPoolExecutor(15, 200, 10,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
            new AsyncTaskRefresh1().executeOnExecutor(exec);

            //利用login信息展示界面
            Glide.with(mContext).load(login.getProfile().getBackgroundUrl()).into(userBackgroundImage);
            //规范Img格式
            Glide.with(mContext).load(login.getProfile().getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userIcon);

            login.getAccount().getId();//用户id
            login.getAccount().getUserName();//用户名
            login.getProfile().getNickname();//昵称
            login.getAccount().getCreateTime();//创建时间
            login.getProfile().getBackgroundUrl();//背景图
            login.getProfile().getAvatarUrl();//头像
            login.getProfile().getGender();//性别1男
            login.getAccount().getVipType();//vip类型
            login.getAccount().getViptypeVersion();
            //歌单数量
            login.getProfile().getPlaylistCount();
        }

        return rootView;
    }

    private void initView(View view){
        myFragmentNestedScrollView = view.findViewById(R.id.myFragmentNestedScrollView);
        userBackgroundImage = view.findViewById(R.id.userBackgroundImage);
        userIcon = view.findViewById(R.id.userIcon);

        coverListView = view.findViewById(R.id.listViewCover);
        coverListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //根据id获取此item对应的歌单id
                Log.e("",mData.get(position).getId()+"");
                //跳转到详情页
                Intent intent = new Intent(getActivity(), CoverActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("coverId",mData.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mData = new LinkedList<Playlist>();

        myFragmentNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    Log.e("=====", "下滑");
                    //设置透明度
/*                    barBackground = getActivity().findViewById(R.id.barBackground);
                    barBackground.getBackground().setAlpha(255);*/
                }
                if (scrollY < oldScrollY) {
                    Log.e("=====", "上滑");

                }

                if (scrollY == 0) {
                    Log.e("=====", "滑到顶部");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.e("=====", "滑到底部");
                }
            }
        });
    }
}
