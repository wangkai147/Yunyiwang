package com.wangk.mymusic.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer player;
    private String url = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public IBinder onBind(Intent intent) {
        //此函数的返回值是针对Bound Service类型的Service才有用的，在Started Service类型中，此函数直接返回 null 即可
        //当执行完了onCreate后，就会执行onBind把操作歌曲的方法返回
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("url");
        Log.i("TAG", "Service接收到：" + url);

        //没有播放源
        if (player == null) {
            //如果为空就new一个
            player = new MediaPlayer();
            try {
                player.setDataSource(url);
                //异步准备
                player.prepareAsync();
                //添加准备好的监听
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //如果准备好了，就会进行这个方法
                        mp.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //判断是否播放的是同一歌曲
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //该方法包含关于歌曲的操作
    public class MyBinder extends Binder {

        //判断是否处于播放状态
        public boolean isPlaying() {
            return player.isPlaying();
        }

        //播放或暂停歌曲
        public void play() {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
            //Log.e("服务", "播放音乐");
        }

        //返回歌曲的长度，单位为毫秒
        public int getDuration() {
            return player.getDuration();
        }

        //返回歌曲目前的进度，单位为毫秒
        public int getCurrenPostion() {
            return player.getCurrentPosition();
        }

        //设置歌曲播放的进度，单位为毫秒
        public void seekTo(int mesc) {
            player.seekTo(mesc);
        }
    }
}