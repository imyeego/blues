package com.example.okhttp3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private String mBasePath = "http://192.168.1.104:8080/helloworld/";
    OkHttpClient okHttpClient = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.tv_result);
    }

    public void doGet(View view) {
        Request.Builder builder= new Request.Builder();
        Request request = builder.get().url(mBasePath
                + "login?username=liuzhao&password=123456").build();
        executeRequest(request);
    }

    public void doPost(View view) {
        FormBody.Builder requestBuilder = new FormBody.Builder();
        RequestBody requestBody = requestBuilder
                .add("username", "liuzhao")
                .add("password", "12345").build();
        Request.Builder builder= new Request.Builder();

        Request request = builder.url(mBasePath + "login").post(requestBody).build();
        executeRequest(request);
    }

    public void doPostString(View view) {
    }

    private void executeRequest(Request request) {
        Call call  = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("onFailure" + e.getMessage());
                mTextView.setText("Failed!");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                L.e("onResponse" + res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(res);
                    }
                });
            }
        });
    }
}
