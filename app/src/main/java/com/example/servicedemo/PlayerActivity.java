package com.example.servicedemo;

import static com.example.servicedemo.interfaces.IPlayControl.STATE_PAUSE;
import static com.example.servicedemo.interfaces.IPlayControl.STATE_PLAY;
import static com.example.servicedemo.interfaces.IPlayControl.STATE_STOP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.servicedemo.interfaces.IPlayControl;
import com.example.servicedemo.interfaces.IPlayerViewControl;
import com.example.servicedemo.services.PlayerService;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";
    private SeekBar seekBar;
    private Button playBtn;
    private Button stopBtn;
    private PlayerConnection playerConnection;
    private IPlayControl iPlayerControl;
    private boolean isUserTouchSeekBar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        
        initView();

        initEvent();
        //启动服务
        initService();
        //绑定服务
        initBindService();
    }

    private void initService() {
        Log.d(TAG, "initService: ");
        startService(new Intent(this,PlayerService.class));
    }

    /**
     * 绑定服务
     */
    private void initBindService() {
        Log.d(TAG, "initBindService: ");

        Intent intent = new Intent(this, PlayerService.class);

        if (playerConnection == null) {
            playerConnection = new PlayerConnection();
        }
        bindService(intent,playerConnection,BIND_AUTO_CREATE);
    }
    private class PlayerConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            //service是服务的onBind()方法返回的IBinder，在Service中实现该接口
            iPlayerControl = (IPlayControl) service;
            iPlayerControl.registerViewController(iPlayerViewControl);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            iPlayerControl = null;
        }
    }

    private void initEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度条发生改变
                
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isUserTouchSeekBar = true;
            }

            /**
             * 停止追踪滑块移动，并设置播放进度
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isUserTouchSeekBar = false;
                int touchProgress = seekBar.getProgress();
                Log.d(TAG, "onStopTrackingTouch: " + touchProgress);
                if (iPlayerControl != null) {
                    iPlayerControl.seekto(touchProgress);
                }
            }
        });

        //播放或暂停按钮点击
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayerControl != null) {
                    iPlayerControl.playOrPause();
                }
            }
        });

        //关闭按钮点击
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayerControl != null) {
                    iPlayerControl.stop();
                }
            }
        });
    }

    private void initView() {
        seekBar = this.findViewById(R.id.seekBar);
        playBtn = this.findViewById(R.id.playBtn);
        stopBtn = this.findViewById(R.id.stopBtn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(playerConnection!=null){
            Log.d(TAG, "onDestroy: ");

            iPlayerControl.unRegisterViewController();

            unbindService(playerConnection);
            playerConnection = null;
        }
    }

    //Activity只负责界面的展示
    //将改变UI的任务交给服务去完成，
    //Activity和服务之间通过接口去定义操作。
    private IPlayerViewControl iPlayerViewControl = new IPlayerViewControl() {
        @Override
        public void onPlayerStateChange(int state) {
            switch (state){
                case STATE_PLAY:
                    //播放时修改按钮为暂停
                    playBtn.setText("暂停");
                    break;
                case STATE_PAUSE:
                case STATE_STOP:
                    playBtn.setText("播放");
                    break;
            }
        }

        @Override
        public void onSeekChange(int seek) {
            //当用户的受触摸到进度条时
            if(!isUserTouchSeekBar){
                seekBar.setProgress(seek);
            }
        }
    };
}