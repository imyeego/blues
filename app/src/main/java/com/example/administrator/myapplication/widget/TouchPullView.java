package com.example.administrator.myapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import com.example.administrator.myapplication.CirclePath;

import java.lang.Math;

/**
 * Created by Administrator on 2017/9/10 0010.
 */

public class TouchPullView extends View {
    private Paint mCirclePaint;
    private int mCircleRadius = 100;
    private int circleRadius = 90;
    private float mCirclePointY;
    private float mMoveSize;  //进度
    private float mDragHeight = 400;  //可拖动高度
    private float mCirclePointX = this.getWidth() >> 1;


    private ValueAnimator valueAnimator;
    private Paint mPathPaint;
    private Path mPath = new Path();
    private int mTargetWidth = 150; //目标宽度
    private int mTargetGravityHeigh = 8; //圆心高度，决定控制点y坐标
    private double mTangentAngle = 120 * Math.PI / 180; //角度变换

    public TouchPullView(Context context) {
        super(context);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xFFDB7093);
        mCirclePaint = p;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(5);

        p.setColor(0xFFDDA0DD);
        mPathPaint = p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float x = getWidth() >> 1;
//        float y = getHeight() >> 1;
        canvas.drawPath(mPath, mPathPaint);
        canvas.drawCircle(mCirclePointX, mCirclePointY, circleRadius, mCirclePaint);
    }

    /**
     * 当测量时触发
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度的意图，宽度的类型
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int measureWidth = 0;
        int iWidth = 2*mCircleRadius + getPaddingLeft() + getPaddingRight();
        //高度的类型
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int measureHeight;
        /*int iHeight = (int)(mMoveSize < 300 ? mMoveSize
                : mMoveSize < 400 ? 300 + CirclePath.onPath(mMoveSize - 300)
                : mDragHeight
                + 0.5f + getPaddingTop() + getPaddingBottom());*/
        int iHeight = (int)( (mMoveSize < 400 ? mMoveSize : mDragHeight)+ 0.5f + getPaddingBottom() + getPaddingTop());


//        if(widthMode == MeasureSpec.EXACTLY){
//            //确切的
//            measureWidth = width;
//        }else if (widthMode == MeasureSpec.AT_MOST){
//            //最多的
//            measureWidth = Math.min(iWidth, width);
//        }else
//            measureWidth = iWidth;
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                measureWidth = width;
                break;
            case MeasureSpec.AT_MOST:
                measureWidth = Math.min(iWidth, width);
                break;
            case MeasureSpec.UNSPECIFIED:
                measureWidth = iWidth;
                break;
            default:
                break;

        }


        if(heightMode == MeasureSpec.EXACTLY){
            //确切的
            measureHeight = height;
        }else if (heightMode == MeasureSpec.AT_MOST){
            //最多的
            measureHeight = Math.min(iHeight, height);
        }else
            measureHeight = iHeight;


        setMeasuredDimension(measureWidth, measureHeight);
        Log.e("TAG", "P" + measureHeight);

    }

    /**
     * 当大小改变时触发
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCirclePointX = getWidth() >> 1;
        mCirclePointY = getHeight() - mCircleRadius - 5.5f - getPaddingBottom();
        updatePathLayout();
    }

    private void updatePathLayout() {
        final float progress = getProgress(mMoveSize);
        final double mAangle = mTangentAngle * 180 / Math.PI;
        final Path path = mPath;
        RectF rectF = new RectF(mCirclePointX - mCircleRadius,
                mCirclePointY - mCircleRadius,
                mCirclePointX + mCircleRadius,
                mCirclePointY + mCircleRadius);
        path.reset();


        float valueOfLeftStartX = getValueByLine(0, (getWidth() - mTargetWidth) >> 1, progress);
        float valueOfRightStartX = getValueByLine(getWidth(), (getWidth() + mTargetWidth)>>1, progress );
        final float valueOfLeftStartY  = 0;
        final float valueOfRightStartY = 0;


        float valueOfLeftEndX = (float) (mCirclePointX - mCircleRadius * Math.sin(mTangentAngle * progress));
        float valueOfLeftEndY = (float) (mCirclePointY + mCircleRadius * Math.cos(mTangentAngle * progress));
        float valueOfLeftControlY = mTargetGravityHeigh * progress;
        float valueOfLeftControlX = (float) (valueOfLeftEndX -
                (valueOfLeftEndY - valueOfLeftControlY) / Math.tan(mTangentAngle * progress));

        float valueOfRightEndX = (float) (mCirclePointX + mCircleRadius * Math.sin(mTangentAngle * progress));
        float valueOfRightEndY = (float) (mCirclePointY + mCircleRadius * Math.cos(mTangentAngle * progress));
        float valueOfRightControlY = mTargetGravityHeigh * progress;
        float valueOfRightControlX = (float) (valueOfRightEndX +
                (valueOfRightEndY - valueOfRightControlY) / Math.tan(mTangentAngle * progress));

        //path.moveTo(valueOfLeftStartX, valueOfLeftStartY);
        path.moveTo(valueOfRightStartX, valueOfRightStartY);
        path.quadTo(valueOfRightControlX, valueOfRightControlY, valueOfRightEndX, valueOfRightEndY);
        path.arcTo(rectF, (float) (90 - mAangle * progress),
                (float) (2* mAangle * progress), false);

        path.quadTo(valueOfLeftControlX, valueOfLeftControlY, valueOfLeftStartX, valueOfLeftStartY);


    }
    //释放动画
    public void release(){
        if(valueAnimator == null){
//            final float progress = getProgress(mMoveSize);
            ValueAnimator animator = ValueAnimator.ofFloat(mMoveSize, 0f);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object val = animation.getAnimatedValue();
                    if(val instanceof Float)
                        getMoveSize((Float) val);
                }
            });
            valueAnimator = animator;
        }else{
            valueAnimator.cancel();
            valueAnimator.setFloatValues(mMoveSize, 0f);
        }
        valueAnimator.start();
    }



    /**
     * 获取当前值
     * @param start
     * @param end
     * @param progress
     * @return
     */
    private float getValueByLine(float start, float end, float progress){
        return start + (end - start) * progress;
    }

//    private float getValueByAngle(float minangle, float maxangle, float progress){
//        return minangle + (maxangle - minangle) * progress;
//    }

    public float getProgress(float movesize){
        return movesize / mDragHeight < 1 ? movesize / mDragHeight :1;
    }

    public void getMoveSize(float movesize){
        mMoveSize = movesize;
        requestLayout();  //请求重新测量
//        Log.e("TAG","P:" + movesize);
    }


}
