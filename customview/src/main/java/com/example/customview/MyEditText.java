package com.example.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class MyEditText extends EditText implements View.OnFocusChangeListener,View.OnClickListener{

    private Paint paint;

    private Path path;

    private int color = 0xffbbbbbb;

    public MyEditText(Context context) {
        this(context, null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        this.setFocusable(true);
//        this.setFocusableInTouchMode(true);

        super.setOnFocusChangeListener(this);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) 2.0);
        paint.setDither(true);
        path = new Path();

        path.moveTo(0, getLineCount() * getLineHeight() + 5);
        path.rLineTo(getMeasuredWidth(), 0);
        path.close();
        canvas.drawPath(path, paint);

        super.onDraw(canvas);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            color = 0xff00dc66;
            this.setFocusable(true);
            this.setFocusableInTouchMode(true);
            this.requestFocus();
            invalidate();
        }
        else{
            color = 0xffbbbbbb;
            this.setFocusable(false);
            this.setFocusableInTouchMode(false);
            this.clearFocus();

            invalidate();
        }

    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) ((getContext()).getSystemService(Context.INPUT_METHOD_SERVICE));

        color = 0xff00dc66;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.requestFocus();
        imm.showSoftInput(this, 0);
        invalidate();
    }
}
