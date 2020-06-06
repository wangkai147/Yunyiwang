package com.wangk.mymusic.PlayPage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;

import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.Bean.SongRoot;

import com.wangk.mymusic.PlayPage.Bean.LrcRootBean;
import com.wangk.mymusic.R;
import com.wangk.mymusic.Service.MusicService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
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

import static com.wangk.mymusic.Home.UI.PermisionUtils.verifyStoragePermissions;

public class PlayActivity extends BaseActivity {

    private SongRoot songRoot = null;
    private long id = 0;

    private MyConnection conn;
    private MusicService.MyBinder musicControl;

    private Button playBtn;
    private SeekBar seekBar;
    private LrcRootBean lrcRootBean;
    private static final int UPDATE_PROGRESS = 0;
    private OkHttpClient client = new OkHttpClient();


    @Override
    protected int setLayout() {
        return R.layout.activity_play;
    }

    @Override
    protected void initView() {
        playBtn = findViewById(R.id.play);
        seekBar = findViewById(R.id.sb);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度条改变
                if (fromUser){
                    musicControl.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始触摸进度条
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //停止触摸进度条
            }
        });
    }

    //使用handler定时更新进度条
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    updateProgress();
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        //获取上个页面的songRoot
        songRoot = (SongRoot)getIntent().getSerializableExtra("songRoot");
        //歌曲id用于获取歌词
        id = getIntent().getLongExtra("mId",0);
        Executor exec = new ThreadPoolExecutor(15, 200, 10,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        new AsyncTaskRefresh().executeOnExecutor(exec);
    }

    //网络请求获取用户详情
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
                        .url("https://vast-coast-94601.herokuapp.com/lyric")
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
                lrcRootBean = gson.fromJson(result, LrcRootBean.class);

            } else {

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        initData();

        Intent intent = new Intent(PlayActivity.this, MusicService.class);
        String urt = null;

        for(int i=0;i<songRoot.getData().size();i++){
            if(songRoot.getData().get(i).getId()==id){
                //i为data下标
                urt = songRoot.getData().get(i).getUrl();
            }
        }

        intent.putExtra("url",urt);

        conn = new PlayActivity.MyConnection();
        //使用混合的方法开启服务，
        startService(intent);
        bindService(intent, conn, BIND_AUTO_CREATE);

        initView();

    }

    private class MyConnection implements ServiceConnection {

        //服务启动完成后会进入到这个方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得service中的MyBinder
            musicControl = (MusicService.MyBinder) service;
            //更新按钮的文字
            updatePlayText();
            //设置进度条的最大值
            seekBar.setMax(musicControl.getDuration());
            //设置进度条的进度
            seekBar.setProgress(musicControl.getCurrenPostion());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //进入到界面后开始更新进度条
        if (musicControl != null){
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出应用后与service解除绑定
        unbindService(conn);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止更新进度条的进度
        handler.removeCallbacksAndMessages(null);
    }

    //更新进度条
    private void updateProgress() {
        int currenPostion = musicControl.getCurrenPostion();
        seekBar.setProgress(currenPostion);
        //使用Handler每500毫秒更新一次进度条
        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);
    }


    //更新按钮的文字
    public void updatePlayText() {
        if (musicControl.isPlaying()) {
            playBtn.setText("暂停");
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        } else {
            playBtn.setText("播放");
        }
    }

    //调用MyBinder中的play()方法
    public void play() {
        musicControl.play();
        updatePlayText();
    }
}
