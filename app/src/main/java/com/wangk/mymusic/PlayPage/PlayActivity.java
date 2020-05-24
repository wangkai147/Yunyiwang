package com.wangk.mymusic.PlayPage;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.wangk.mymusic.Base.BaseActivity;
import com.wangk.mymusic.Home.Bean.SongRoot;
import com.wangk.mymusic.R;
import com.wangk.mymusic.Service.MusicService;

public class PlayActivity extends BaseActivity {

    private SongRoot songRoot;

    private MyConnection conn;
    private MusicService.MyBinder musicControl;

    private Button playBtn;
    private SeekBar seekBar;
    private static final int UPDATE_PROGRESS = 0;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        //获取banners信息
        initData();

        Intent intent = new Intent(PlayActivity.this, MusicService.class);
        intent.putExtra("url",songRoot.getData().get(0).getUrl());
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
