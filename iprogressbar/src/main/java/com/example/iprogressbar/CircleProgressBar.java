package com.example.iprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class CircleProgressBar extends ProgressBar {
    private final static int DEFAULT_TEXT_SIZE = 10;//SP
    private final static int DEFAULT_TEXT_COLOR = 0xFFFC00D1;//
    private final static int DEFAULT_UNREACH_COLOR = 0XFFD3D6DA;//SP
    private final static int DEFAULT_UNREACH_WIDTH = 2;//DP
    private final static int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;//SP
    private final static int DEFAULT_REACH_WIDTH = 4;//DP
    private final static int DEFAULT_RADIUS = 50;//DP

    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mUnreachColor = DEFAULT_UNREACH_COLOR;
    private int mUnreachWidth = dp2px(DEFAULT_UNREACH_WIDTH);
    private int mReachColor = DEFAULT_REACH_COLOR;
    private int mReachWidth = dp2px(DEFAULT_REACH_WIDTH);
    private int mRadius = dp2px(DEFAULT_RADIUS);
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path mPath = new Path();



    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtainStyledAttrs(attrs);
    }

    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mTextSize = (int) ta.getDimension(R.styleable.CircleProgressBar_text_size, mTextSize);
        mTextColor = ta.getColor(R.styleable.CircleProgressBar_text_color, mTextColor);
        mReachColor = ta.getColor(R.styleable.CircleProgressBar_reach_color, mReachColor);
        mReachWidth = (int) ta.getDimension(R.styleable.CircleProgressBar_reach_width, mReachWidth);
        mUnreachColor = ta.getColor(R.styleable.CircleProgressBar_unreach_color, mUnreachColor);
        mUnreachWidth = (int) ta.getDimension(R.styleable.CircleProgressBar_unreach_width, mUnreachWidth);
        mRadius = (int) ta.getDimension(R.styleable.CircleProgressBar_radius, mRadius);
        ta.recycle();

        mPaint.setTextSize(mTextSize);
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setmUnreachColor(int mUnreachColor) {
        this.mUnreachColor = mUnreachColor;
    }

    public void setmUnreachWidth(int mUnreachWidth) {
        this.mUnreachWidth = mUnreachWidth;
    }

    public void setmReachColor(int mReachColor) {
        this.mReachColor = mReachColor;
    }

    public void setmReachWidth(int mReachWidth) {
        this.mReachWidth = mReachWidth;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = mPaint.descent() + mPaint.ascent();

        canvas.translate(getPaddingLeft(), getPaddingTop());

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachWidth);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachWidth);
        float sweepAngle = getProgress() *1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, mRadius *2, mRadius *2),
                -90, sweepAngle, false, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight /2 , mPaint);

        canvas.restore();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int result = 2 * (mRadius + mReachWidth) + getPaddingLeft() + getPaddingRight();
        int iWidth = resolveSize(result, widthMeasureSpec);
        int iHeight = resolveSize(result, heightMeasureSpec);
        setMeasuredDimension(iWidth, iHeight);
//        mRadius = 80;

    }
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = 2 * (mRadius + mReachWidth) + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, size);
            default:
                break;
        }
        return result;
    }


    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = 2 * (mRadius + mReachWidth) + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, size);
            default:
                break;
        }
        return result;
    }
    private int sp2px(int spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                getResources().getDisplayMetrics());
    }
    private int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getResources().getDisplayMetrics());
    }
}
