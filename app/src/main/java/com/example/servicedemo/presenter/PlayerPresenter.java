package com.example.servicedemo.presenter;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.util.Log;

import com.example.servicedemo.interfaces.IPlayControl;
import com.example.servicedemo.interfaces.IPlayerViewControl;

import java.io.IOException;

public class PlayerPresenter extends Binder implements IPlayControl {

    private static final String TAG = "PlayerPresenter";
    private IPlayerViewControl viewController;
    private int currentState = STATE_STOP;
    private MediaPlayer mediaPlayer;

    @Override
    public void registerViewController(IPlayerViewControl viewController) {
        this.viewController = viewController;
    }

    @Override
    public void unRegisterViewController() {
        viewController = null;
    }

    @Override
    public void playOrPause() {
        Log.d(TAG, "playOrPause: ");
        if(currentState == STATE_STOP){
            initPlayer();

            try {
                mediaPlayer.setDataSource("/sdcard/Music/betheone.mp3");
                mediaPlayer.prepare();
                mediaPlayer.start();
                currentState = STATE_PLAY;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (currentState == STATE_PLAY){
            if(mediaPlayer != null){
                mediaPlayer.pause();
                currentState = STATE_PAUSE;
            }
        }else if(currentState == STATE_PAUSE){
            if(mediaPlayer != null){
                mediaPlayer.start();
                currentState = STATE_PLAY;
            }
        }

        //更新播放视图，不然按钮不会变
        if (viewController != null) {
            viewController.onPlayerStateChange(currentState);
        }
    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop: ");

        if(currentState == STATE_PLAY){
            if(mediaPlayer != null){
                mediaPlayer.stop();
                currentState = STATE_STOP;
                mediaPlayer.release();
                mediaPlayer = null;

                //更新播放视图
                if (viewController != null) {
                    viewController.onPlayerStateChange(currentState);
                }
            }
        }
    }

    @Override
    public void seekto(int seek) {
        Log.d(TAG, "seekto: " + seek);
    }
}
