package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.servicedemo.interfaces.ICommunciation;
import com.example.servicedemo.services.FirstService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button mStart;
    private Button mStop;
    private Button mBindService;
    private Button mUnBindService;
    private ServiceConnection mConnection;
    private boolean mIsServiceBinded;
    private ICommunciation mRemoteBinder;
    private Button invokeMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();

    }

    private void initView() {
        mStart = this.findViewById(R.id.start);
        mStop = this.findViewById(R.id.stop);
        mBindService = this.findViewById(R.id.bindService);
        mUnBindService = this.findViewById(R.id.unBindService);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected...");
                mRemoteBinder = (ICommunciation) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected...");
                mRemoteBinder = null;
            }
        };
        invokeMethod = this.findViewById(R.id.invokeMethod);
    }

    private void initListener() {
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(v);
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(v);
            }
        });
        mBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(v);
            }
        });
        mUnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unBindService(v);
            }
        });
        invokeMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServiceMethod(v);
            }
        });
    }

    public void startService(View view){
        Log.d(TAG, "startService...");
        startService(new Intent(this, FirstService.class));
    }

    public void stopService(View view){
        Log.d(TAG, "stopService...");
        stopService(new Intent(this, FirstService.class));
    }

    public void bindService(View view){
        Log.d(TAG, "bindService...");
        Intent intent = new Intent();
        intent.setClass(this,FirstService.class);

        mIsServiceBinded =  bindService(intent,mConnection, BIND_AUTO_CREATE);
    }

    public void unBindService(View view){
        if(mConnection != null && mIsServiceBinded){
            Log.d(TAG, "unBindService...");
            unbindService(mConnection);
        }
    }

    public void callServiceMethod(View view){
        Log.d(TAG, "callServiceMethod...");
        mRemoteBinder.callServiceInnerMethod();
    }

}