package com.example.images;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String mBasePath = "http://192.168.1.101:8080/helloworld/timeline";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Request.Builder builder;
    private TextView mTextView;
    private ListView listView;
    private List<ItemEntity> list;
    private ListItemAdapter listItemAdapter;
    private Context context;
    private RelativeLayout relativeLayout;
    private RelativeLayout mask;

    private Handler handler;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_main);
        Fade fade = new Fade();
        fade.setDuration(300);
        getWindow().setExitTransition(fade);
        mTextView = findViewById(R.id.main_tv);
        listView = findViewById(R.id.main_lv);
        relativeLayout = findViewById(R.id.main_rl);


//        WindowManager wm = this.getWindowManager();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        screenHeight = outMetrics.heightPixels;


        mask = findViewById(R.id.mask);

//        mask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
//
//                mask.setVisibility(View.GONE);
//                flag = 0;
//            }
//        });
        context = this;


        builder = new Request.Builder();
        Request request = builder.get().url(mBasePath).build();
        executeRequest(request);

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1){
                    mTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listItemAdapter = new ListItemAdapter(context, list);

                    listView.setAdapter(listItemAdapter);
                }else
                    mTextView.setVisibility(View.VISIBLE);

            }
        };
    }


    private void executeRequest(Request request) {
        Call call  = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mTextView.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Message msg = new Message();
                msg.what = 0;
                if (null != json){
                    msg.what = 1;
                    list = new ArrayList<>();
                    list = JSON.parseArray(json, ItemEntity.class);

                }
                handler.sendMessage(msg);


            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.more:
                int height = relativeLayout.getHeight();

                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.menu_view, null);
                final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(lp);

                lp.setMargins(0, height, 0, 0);
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                view.measure(w, h);
                int menuHeight = view.getMeasuredHeight();
                ValueAnimator animator;

                if (flag == 0){
                    mask.setVisibility(View.VISIBLE);
                    relativeLayout.addView(view);

                    animator = ValueAnimator.ofFloat(height,height - menuHeight);
                    animator.setDuration(200);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            lp.setMargins(0, (int) value, 0, 0);
                            relativeLayout.requestLayout();
                        }
                    });
                    animator.start();

                    flag = 1;
                }else{
                    final View viewMenu = relativeLayout.getChildAt(relativeLayout.getChildCount() - 1);
                    animator = ValueAnimator.ofFloat(height - menuHeight, height);
                    animator.setDuration(200);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            lp.setMargins(0, (int) value, 0, 0);
                            Log.d("value", String.valueOf(lp.topMargin));
                            viewMenu.setLayoutParams(lp);
                            relativeLayout.requestLayout();
                        }
                    });
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout.removeViewAt(relativeLayout.getChildCount() - 1);
                            mask.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator.start();


                    flag = 0;
                }

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
