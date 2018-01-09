package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class BazierPath extends View {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();

    public BazierPath(Context context) {
        super(context);
        init();
    }

    public BazierPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BazierPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BazierPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){
        Paint paint = mPaint;
        Path path = mPath;

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);

        //one
        path.moveTo(1000, 0);
        //path.lineTo(300, 300);
        //two
        path.quadTo(800, 0, 600, 300);

        RectF rectF = new RectF(400, 200, 600, 400);
        path.arcTo(rectF, 0, 180, false);
        //relative
        path.quadTo(200, 0, 0, 0);
        //path.moveTo(300, 500);
        //path.rCubicTo(100,-100, 300, 100,400, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.drawPoint(200,0, mPaint);
        canvas.drawPoint(400,0, mPaint);
    }
}
