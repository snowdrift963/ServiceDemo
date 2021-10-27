package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.servicedemo.interfaces.ICommunciation;
import com.example.servicedemo.services.SecondService;

public class SecondActivity extends AppCompatActivity {

    private boolean mIsBind;
    private ICommunciation mCommunication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    /**
     * 开启服务
     * @param view
     */
    public void startService(View view) {
        Intent intent = new Intent(this, SecondService.class);
        startService(intent);
    }

    /**
     * 绑定服务
     * @param view
     */
    public void bindService(View view) {
        Intent intent = new Intent(this, SecondService.class);
        mIsBind = bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCommunication = (ICommunciation) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCommunication = null;
        }
    };

    /**
     * 调用服务方法
     * @param view
     */
    public void callServiceMethod(View view) {
        if (mCommunication != null) {
            mCommunication.callServiceInnerMethod();
        }
    }

    /**
     * 解绑服务
     * @param view
     */
    public void unBindService(View view) {
        if(mIsBind && mConnection != null){
            unbindService(mConnection);
        }
    }

    /**
     * 停止服务
     * @param view
     */
    public void stopService(View view) {
        //停止服务
        Intent intent = new Intent(this, SecondService.class);
        stopService(intent);
    }
}