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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wangk.mymusic.Home.Bean.BannerRoot;
import com.wangk.mymusic.Home.Bean.Banners;
import com.wangk.mymusic.Home.Fragment.Adapter.ImageNetAdapter;
import com.wangk.mymusic.Home.Fragment.Bean.DataBean;

import com.wangk.mymusic.Home.UI.SongpageActivity;
import com.wangk.mymusic.MainActivity;
import com.wangk.mymusic.PlayPage.PlayActivity;
import com.youth.banner.Banner;

import com.wangk.mymusic.R;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
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

public class RecommendFragment extends Fragment {


    private View rootView;

    private Context mContext;//this

    private Banner banner;
    private BannerRoot bannerRoot;
    //private List<String> imageUrl = new ArrayList<>();
    private List<Banners> banners;
    private List<DataBean> dataBeans = new ArrayList<>();

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
                //获取ImageUrl保存到list中
                banners = bannerRoot.getBanners();
                for(int i = 0;i<banners.size();i++){
                    //imageUrl.add(banners.get(i).getPic());
                    dataBeans.add(new DataBean(banners.get(i).getPic(),null,1));
                }

                Toast.makeText(getActivity(),dataBeans.get(0).getImageUrl(),Toast.LENGTH_SHORT).show();
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
        Executor exec = new ThreadPoolExecutor(15, 200, 10,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        new AsyncTaskRefresh().executeOnExecutor(exec);//异步请求

        //设置适配器设置样式
        ImageNetAdapter adapter = new ImageNetAdapter(dataBeans);

        banner.setAdapter(adapter).start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                /* //获取当前item对应数据
                banners.get(position);*/
                Snackbar.make(banner, "((DataBean) data).title"+position, Snackbar.LENGTH_SHORT).show();

                //判断是不是单曲
                if(banners.get(position).getSong()!=null){
                    //跳转到播放页
                    Intent intent = new Intent(getActivity(), PlayActivity.class);
                    intent.putExtra("banner",banners.get(position));
                    getActivity().startActivity(intent);
                }
            }
        });

        return rootView;
    }

    private void initView(View view){
        banner = view.findViewById(R.id.banner);
        banner.setBannerRound(BannerUtils.dp2px(5));
        banner.setBannerGalleryMZ(20);

/*        //设置指示器
        banner.setIndicator(new CircleIndicator(getActivity()));*/
    }
    private void initData(){

    }
}
