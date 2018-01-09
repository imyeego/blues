package com.example.myhandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/11/25 0025.
 */

public class SecondActivity extends FragmentActivity {

    private Button button1, button2;

    private Handler handler;

    private Handler handlerThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e("id","main handler");
                Message message = new Message();
                handlerThread.sendMessageDelayed(message, 1000);
            }
        };

        HandlerThread thread = new HandlerThread("handlerthread");
        thread.start();

        handlerThread = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
                Log.e("id", "handler thread");
                Message message = new Message();
                handler.sendMessageDelayed(message, 1000);

            }
        };
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(1);
            }
        });
    }
}
