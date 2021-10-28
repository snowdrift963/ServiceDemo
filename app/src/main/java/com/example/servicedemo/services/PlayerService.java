package com.example.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.servicedemo.presenter.PlayerPresenter;

public class PlayerService extends Service {

    private PlayerPresenter playerPresenter;

    @Override
    public void onCreate() {
        super.onCreate();

        if (playerPresenter == null) {
            playerPresenter = new PlayerPresenter();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //此处返回的IBinder将作为Activity中Connect的onServiceConnected的参数
        //返回一个实现了的IPlayControl接口
        return playerPresenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerPresenter = null;
    }
}