package com.wangk.mymusic.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wangk.mymusic.Home.Bean.BannerRoot;
import com.wangk.mymusic.Home.Fragment.Adapter.ImageNetAdapter;
import com.wangk.mymusic.Home.Fragment.Bean.DataBean;
import com.wangk.mymusic.Home.HomeActivity;
import com.wangk.mymusic.Login.Bean.Login;
import com.wangk.mymusic.Login.UI.LoadingActivity;
import com.wangk.mymusic.Login.UI.LoginActivity;
import com.youth.banner.Banner;

import com.wangk.mymusic.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecommendFragment extends Fragment {


    private View rootView;

    private Context mContext;//this

    private Banner banner;
    private BannerRoot bannerRoot;

    private OkHttpClient client = new OkHttpClient();


    public RecommendFragment() {
        // Required empty public constructor
        this.mContext = getActivity();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private class AsyncTaskRefresh extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            //添加线程任务(网络请求)
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("type","1")
                        .build();

                //推荐歌单
                Request request = new Request.Builder()
                        .url("https://vast-coast-94601.herokuapp.com/banner")
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
                //login存储登录数据
                bannerRoot = gson.fromJson(result, BannerRoot.class);
                Toast.makeText(getActivity(),"获取成功",Toast.LENGTH_SHORT).show();

                //获取ImageUrl

                //保存到list中

            } else {
                Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recommend, container, false);
        initView(rootView);
        //网络请求获取banner图片数据

        //url保存入集合

        //设置适配器
        ImageNetAdapter adapter = new ImageNetAdapter(DataBean.getImageData());
        banner.setAdapter(adapter)
        .start();

        new AsyncTaskRefresh().execute();//异步请求

/*        //设置指示器
        banner.setIndicator(new CircleIndicator(mContext));*/

        return rootView;
    }

    private void initView(View view){
        banner = view.findViewById(R.id.banner);
    }
    private void initData(){

    }
}
