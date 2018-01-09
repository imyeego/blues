package com.example.iprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class HorizontalProgressBar extends ProgressBar {

    private final static int DEFAULT_TEXT_SIZE = 10;//SP
    private final static int DEFAULT_TEXT_COLOR = 0xFFFC00D1;//
    private final static int DEFAULT_UNREACH_COLOR = 0XFFD3D6DA;//SP
    private final static int DEFAULT_UNREACH_HEIGHT = 2;//DP
    private final static int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;//SP
    private final static int DEFAULT_REACH_HEIGHT = 2;//DP
    private final static int DEFAULT_TEXT_OFFSET = 10;//DP

    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mUnreachColor = DEFAULT_UNREACH_COLOR;
    private int mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    private int mReachColor = DEFAULT_REACH_COLOR;
    private int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mRealWidth;




    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(attrs);
    }

    protected void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setmUnreachColor(int mUnreachColor) {
        this.mUnreachColor = mUnreachColor;
    }

    public void setmUnreachHeight(int mUnreachHeight) {
        this.mUnreachHeight = mUnreachHeight;
    }

    public void setmReachColor(int mReachColor) {
        this.mReachColor = mReachColor;
    }

    public void setmReachHeight(int mReachHeight) {
        this.mReachHeight = mReachHeight;
    }

    public void setmTextOffset(int mTextOffset) {
        this.mTextOffset = mTextOffset;
    }

    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.MyProgressBar);
        mTextSize = (int) ta.getDimension(R.styleable.MyProgressBar_text_size, mTextSize);
        mTextColor = ta.getColor(R.styleable.MyProgressBar_text_color, mTextColor);
        mTextOffset = (int) ta.getDimension(R.styleable.MyProgressBar_text_offset, mTextOffset);
        mReachColor = ta.getColor(R.styleable.MyProgressBar_reach_color, mReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.MyProgressBar_reach_height, mReachHeight);
        mUnreachColor = ta.getColor(R.styleable.MyProgressBar_unreach_color, mUnreachColor);
        mUnreachHeight = (int) ta.getDimension(R.styleable.MyProgressBar_unreach_height, mUnreachHeight);

        ta.recycle();
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mRealWidth = getMeasuredWidth();

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() >> 1);
        boolean noNeedUnReach = false;
        String text = getProgress() + "%";
        int textWidth = (int)mPaint.measureText(text);

        float ratio = getProgress() * 1.0f / getMax();
        float progressX = ratio * mRealWidth;
        float endX = progressX - mTextOffset / 2;
        if (progressX + textWidth > mRealWidth){
            progressX = mRealWidth - textWidth;
            noNeedUnReach = true;
        }

        if (endX > 0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        mPaint.setColor(mTextColor);
        int y = -(int)(mPaint.descent() + mPaint.ascent()) / 2;
        canvas.drawText(text, progressX, y, mPaint);

        if (!noNeedUnReach){
            float start = progressX + mTextOffset/2 + textWidth;
            mPaint.setColor(mUnreachColor);
//            mPaint.setTextSize(mUnreachHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();

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
                int textHeight = (int) (mPaint.descent() - mPaint.ascent());
                result = getPaddingTop() + getPaddingBottom()
                        + Math.max(Math.max(mUnreachColor, mUnreachHeight), Math.abs(textHeight));
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result, size);
                break;
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
