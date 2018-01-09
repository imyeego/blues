package com.example.administrator.myapplication;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myapplication.widget.TouchPullView;

public class MainActivity extends AppCompatActivity {
//    private static  final float TOUCH_MOVE_MAX_Y = 400;
    private  float mTouchMoveStartY;
    private TouchPullView mTouchPullView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTouchPullView = (TouchPullView) findViewById(R.id.touchPull);


        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchMoveStartY = motionEvent.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = motionEvent.getY();
                        if (y >= mTouchMoveStartY){
                            float moveSize = y - mTouchMoveStartY;
                            //float progress = moveSize >= TOUCH_MOVE_MAX_Y
//                                    ? 1 : moveSize / TOUCH_MOVE_MAX_Y;
                            mTouchPullView.getMoveSize(moveSize);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        mTouchPullView.release();
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
