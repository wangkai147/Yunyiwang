package com.wangk.mymusic.Home.UI;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


import com.google.gson.Gson;
import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.Fragment.Adapter.CoverPageAdapter;
import com.wangk.mymusic.Home.UI.Bean.ReMusicRootBean;
import com.wangk.mymusic.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CoverActivity extends BaseActivity {

    private OkHttpClient client = new OkHttpClient();
    private Long id;
    private ListView listView;
    private CoverPageAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initData();
        new AsyncTaskRefresh().execute();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_cover;
    }

    @Override
    protected void initView() {
        listView = findViewById(R.id.coverPageListView);
    }

    @Override
    protected void initData() {
        //获取歌单id
        id = getIntent().getLongExtra("coverId",0);
        Log.e("",id+"");
    }
    //根据歌单id网络请求歌单详情
    private class AsyncTaskRefresh extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            //添加线程任务(网络请求)
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("id",id+"")
                        .build();

                //推荐歌单
                Request request = new Request.Builder()
                        .url("https://vast-coast-94601.herokuapp.com/playlist/detail")
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
                ReMusicRootBean reMusicRootBean = gson.fromJson(result, ReMusicRootBean.class);

                Log.e("",reMusicRootBean.getPlaylist().getTracks().get(0).getName());
                //专辑列表
                //网络请求获取数据
                mAdapter = new CoverPageAdapter(reMusicRootBean.getPlaylist().getTracks(),CoverActivity.this);
                listView.setAdapter(mAdapter);

            } else {

            }
        }

    }
}
