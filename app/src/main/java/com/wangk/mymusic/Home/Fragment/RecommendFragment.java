package com.wangk.mymusic.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wangk.mymusic.Home.Bean.BannerRoot;
import com.wangk.mymusic.Home.Bean.Banners;
import com.wangk.mymusic.Home.Bean.SongRoot;
import com.wangk.mymusic.Home.Fragment.Adapter.ImageNetAdapter;
import com.wangk.mymusic.Home.Fragment.Bean.DataBean;
import com.wangk.mymusic.PlayPage.PlayActivity;
import com.youth.banner.Banner;

import com.wangk.mymusic.R;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

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
    private Banners bannerData;
    private List<DataBean> dataBeans = new ArrayList<>();
    private String idUrl = null;
    private long mId;

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
                //banner集合
                banners = bannerRoot.getBanners();
                for(int i = 0;i<banners.size();i++){
                    //imageUrl.add(banners.get(i).getPic());
                    //遍历bannerImg用于显示
                    dataBeans.add(new DataBean(bannerRoot.getBanners().get(i).getPic(),null,1));
                }
                Log.e("","获取banners成功");
            } else {
                Log.e("","获取banners失败");
            }
        }
    }

    private class AsyncTaskRefresh1 extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            //添加线程任务(网络请求)
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("id",idUrl)
                        .build();

                //推荐歌单
                Request request = new Request.Builder()
                        .url("https://vast-coast-94601.herokuapp.com/song/url")
                        .post(formBody)
                        .build();

                //每次都是新的请求
                OkHttpClient client1 = new OkHttpClient();

                Response response = client1.newCall(request).execute();
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
                SongRoot songRoot = gson.fromJson(result, SongRoot.class);
                //跳转到播放页
                Intent intent = new Intent(getActivity(), PlayActivity.class);
                //传动当前banner数据
                Bundle bundle = new Bundle();
                bundle.putSerializable("songRoot",songRoot);
                bundle.putLong("mId",mId);
                intent.putExtras(bundle);
                startActivity(intent);
                } else {
                Toast.makeText(getActivity(),"获取歌曲失败",Toast.LENGTH_SHORT).show();
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
        new AsyncTaskRefresh().execute();//同步请求

        //设置适配器设置样式
        ImageNetAdapter adapter = new ImageNetAdapter(dataBeans);

        banner.setAdapter(adapter).start();

        //banner事件监听
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                //判断banner对应类型是不是单曲
                if(banners.get(position).getSong()!=null){
                    //遍历banners获取所有MusicId以组成Url
                    idUrl = "";
                    for(int i=0;i<banners.size();i++){
                        if(banners.get(i).getSong()!=null)
                            idUrl = idUrl + banners.get(i).getSong().getId()+",";
                    }
                    idUrl = idUrl.substring(0,idUrl.length()-1);

                    //获取当前点击的banner对应MusicId
                    mId = banners.get(position).getSong().getId();
                    //bannerData = banners.get(position);
                    //进行网络请求获取歌曲url
                    //根据id获取歌曲url
                    new AsyncTaskRefresh1().execute();//同步请求
                } else {
                    //不是单曲获取当前item对应数据banners.get(position);  value类型banners
                    Snackbar.make(banner, "((DataBean) data).title"+position, Snackbar.LENGTH_SHORT).show();
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
