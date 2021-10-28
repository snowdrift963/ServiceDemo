package com.example.servicedemo.interfaces;

public interface IPlayControl {

    int STATE_PLAY = 1;
    int STATE_PAUSE = 2;
    int STATE_STOP = 3;

    /**
     * 把UI的控制接口设置给逻辑层
     * @param viewController
     */
    void registerViewController(IPlayerViewControl viewController);

    void unRegisterViewController();

    void playOrPause();
    void stop();

    /**
     * 设置播放进度
     * @param seek
     */
    void seekto(int seek);
}
