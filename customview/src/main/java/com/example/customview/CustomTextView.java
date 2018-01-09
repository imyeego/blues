package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class CustomTextView extends TextView {

    private Paint paint;

    private final int color = 0xFFDCDCDC;

    private final int radius = 15;

    private Path path;

    private int padding = getPaddingLeft();

    private final int triangleWidth = 20;

    private final int triangleHeight = 20;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        path = new Path();
        path.moveTo(radius + triangleHeight, 0);
        path.lineTo(getMeasuredWidth() - radius - triangleHeight, 0);

        RectF rectF = new RectF(getMeasuredWidth()-radius,0 ,
                getMeasuredWidth(), radius);
        path.arcTo(rectF, -90, 90, false);
        path.rLineTo(0, getMeasuredHeight() - 2 * radius);
        rectF = new RectF(getMeasuredWidth() - radius, getMeasuredHeight()-radius,
                getMeasuredWidth(), getMeasuredHeight());
        path.arcTo(rectF, 0, 90, false);
        path.rLineTo( 2 * radius - getMeasuredWidth(), 0);
        rectF = new RectF(triangleHeight, getMeasuredHeight()-radius,
                radius + triangleHeight, getMeasuredHeight());
        path.arcTo(rectF, 90, 90, false);
        path.rLineTo(0, 2*radius - getMeasuredHeight() + triangleWidth);
        path.rLineTo(-triangleHeight, -triangleWidth / 2);
        path.rLineTo(triangleHeight, -triangleWidth / 2);

        rectF = new RectF(triangleHeight, 0, radius + triangleHeight, radius);
        path.arcTo(rectF, 180, 90, false);

        path.close();
//        canvas.drawArc(rectF, 0, 360, false, paint);
        canvas.drawPath(path, paint);
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
