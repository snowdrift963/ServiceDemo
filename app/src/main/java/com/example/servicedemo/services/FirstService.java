package com.example.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.servicedemo.interfaces.ICommunciation;

import java.security.Provider;

public class FirstService extends Service {

    private static final String TAG = "FirstService";

    private class InnerBinder extends Binder implements ICommunciation {
        @Override
        public void callServiceInnerMethod(){
            sayHello();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind...");
        return new InnerBinder();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate...");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind...");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy...");
        super.onDestroy();
    }

    private void sayHello(){
        Toast.makeText(this,"hello world",Toast.LENGTH_SHORT).show();
    }
}
