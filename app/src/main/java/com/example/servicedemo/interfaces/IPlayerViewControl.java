package com.example.servicedemo.interfaces;

public interface IPlayerViewControl {

    /**
     * 播放状态改变的通知
     * @param state
     */
    void onPlayerStateChange(int state);

    /**
     * 播放进度的改变
     * @param seek
     */
    void onSeekChange(int seek);
}
