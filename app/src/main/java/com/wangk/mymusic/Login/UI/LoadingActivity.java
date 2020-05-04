package com.wangk.mymusic.Login.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.HomeActivity;
import com.wangk.mymusic.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoadingActivity extends BaseActivity {

    private ImageView loadingImageView;
    private String phone;
    private String password;
    private String timestamp;

    private OkHttpClient client = new OkHttpClient();
/*    OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initData();
        new AsyncTaskRefresh().execute();//异步请求
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_loading;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            Toast.makeText(this, "正在登录...请等待", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected void initView() {
        setTranslucent(this);
        loadingImageView = bindView(R.id.loadingImageView);
        Glide.with(this).load(R.drawable.loading).into(loadingImageView);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        phone = bundle.getString("phone");
        password = bundle.getString("password");
        timestamp = (System.currentTimeMillis() / 1000)+"";
        Toast.makeText(LoadingActivity.this, phone+password, Toast.LENGTH_SHORT).show();
    }

    private class AsyncTaskRefresh extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            //添加线程任务(网络请求)
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("phone",phone)
                        .add("password",password)
                        .add("timestamp",timestamp)
                        .build();

                //推荐歌单
                Request request = new Request.Builder()
                        .url("https://vast-coast-94601.herokuapp.com/login/cellphone")
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
                Intent intent = new Intent(LoadingActivity.this, HomeActivity.class);
                intent.putExtra("result",result);
                startActivity(intent);
            } else {
                Toast.makeText(LoadingActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoadingActivity.this,LoginActivity.class));
            }
        }
    }
}
