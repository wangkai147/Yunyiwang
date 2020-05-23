package com.wangk.mymusic.PlayPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.Bean.Banners;
import com.wangk.mymusic.Home.Bean.Data;
import com.wangk.mymusic.Home.Bean.SongRoot;
import com.wangk.mymusic.Home.Fragment.Bean.DataBean;
import com.wangk.mymusic.Home.Fragment.RecommendFragment;
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

public class PlayActivity extends BaseActivity {

    private Banners banners = null;
    private OkHttpClient client = new OkHttpClient();
    private SongRoot songRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initData();


            Executor exec = new ThreadPoolExecutor(15, 200, 10,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
            new AsyncTaskRefresh().executeOnExecutor(exec);//异步请求
    }

    private class AsyncTaskRefresh extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            //添加线程任务(网络请求)
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("id",banners.getSong().getId()+"")
                        .build();

                //推荐歌单
                Request request = new Request.Builder()
                        .url("https://vast-coast-94601.herokuapp.com/song/url")
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
                songRoot = gson.fromJson(result, SongRoot.class);
                for(int i=0;i<songRoot.getData().size();i++){
                    //songRoot.getData().get(i).getUrl();
                    Toast.makeText(PlayActivity.this,songRoot.getData().get(i).getUrl(),Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PlayActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_play;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //获取banner数据
        banners = (Banners) getIntent().getSerializableExtra("banner");
    }
}
