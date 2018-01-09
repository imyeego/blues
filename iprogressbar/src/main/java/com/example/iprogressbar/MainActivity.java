package com.example.iprogressbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends Activity {
    private static final int MSG = 0x110;
    private HorizontalProgressBar mProgressBar;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int progress = mProgressBar.getProgress();
            mProgressBar.setProgress(++progress);
            if (progress > 100)
                mHandler.removeMessages(MSG);
            mHandler.sendEmptyMessageDelayed(MSG, 100);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.pb);
        mProgressBar.setmReachColor(Color.BLUE);
        mProgressBar.setmTextColor(Color.BLACK);
        mHandler.sendEmptyMessage(MSG);
    }
}
