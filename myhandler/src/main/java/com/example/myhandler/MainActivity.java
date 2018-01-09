package com.example.myhandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private ImageView imageView;

    private int images[] = {R.drawable.a, R.drawable.b, R.drawable.c};

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText(" " + msg.arg1 + "-" + msg.arg2);
        }
    };

    private int index = 0;

    private MyRunnable myRunnable = new MyRunnable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.id_tv);
//        imageView = findViewById(R.id.id_iv);

//        handler.postDelayed(myRunnable, 1000);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Message message = handler.obtainMessage();
//                    Message message1 = handler.obtainMessage();
                    message.arg1 = 88;
                    message.arg2 = 100;
                    Thread.sleep(2000);
//                    message.sendToTarget();
                    handler.sendMessage(message);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText("update thread!");
//                        }
//                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            index ++;
            index = index % 3;
            imageView.setImageResource(images[index]);
            handler.postDelayed(myRunnable, 1000);
        }
    }
}
