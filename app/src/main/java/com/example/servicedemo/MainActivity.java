package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view){
        Log.d(TAG, "startService: ");
        startService(new Intent(this,FirstService.class));
    }

    public void stopService(View view){
        Log.d(TAG, "stopService...");
        stopService(new Intent(this, FirstService.class));
    }

}