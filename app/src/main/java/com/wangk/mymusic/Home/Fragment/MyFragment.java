package com.wangk.mymusic.Home.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.wangk.mymusic.Login.Bean.Login;
import com.wangk.mymusic.R;

import java.io.IOException;
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

    private OkHttpClient client = new OkHttpClient();

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
    //利用login信息展示界面

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
                Toast.makeText(getActivity(),"获取成功",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_SHORT).show();
            }
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

            Glide.with(mContext).load(login.getProfile().getBackgroundUrl()).into(userBackgroundImage);
            Glide.with(mContext).load(login.getProfile().getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userIcon);

            login.getAccount().getId();//用户id
            login.getAccount().getUserName();//用户名
            login.getProfile().getNickname();//昵称
            login.getAccount().getCreateTime();//创建时间
            login.getProfile().getBackgroundUrl();//背景图
            login.getProfile().getAvatarUrl();//头像
            login.getProfile().getGender();//性别1男
            login.getAccount().getVipType();
            login.getAccount().getViptypeVersion();
        }

        return rootView;
    }

    private void initView(View view){
        myFragmentNestedScrollView = view.findViewById(R.id.myFragmentNestedScrollView);
        userBackgroundImage = view.findViewById(R.id.userBackgroundImage);
        userIcon = view.findViewById(R.id.userIcon);
        myFragmentNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    Log.e("=====", "下滑");
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
