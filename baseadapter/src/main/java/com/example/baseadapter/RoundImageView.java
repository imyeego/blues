package com.example.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/11/19 0019.
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private int width, height;

    private Path path;
    private Paint paint;
    private int round = 30;

    private final int color = 0xff424242;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

//        if (width > round && height > round) {
//            path.moveTo(round, 0);
//            path.lineTo(width - round, 0);
//            path.quadTo(width, 0, width, round);
//            path.lineTo(width, height - round);
//            path.quadTo(width, height, width - round, height);
//            path.lineTo(round, height);
//            path.quadTo(0, height, 0, height - round);
//            path.lineTo(0, round);
//            path.quadTo(0, 0, round, 0);
//            canvas.clipPath(path);
//        }

        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getRoundBitmap(bitmap, 20);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);

        }
        super.onDraw(canvas);
    }

    private Bitmap getRoundBitmap(Bitmap bitmap, int round) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        canvas.drawARGB(0, 0, 0, 0);

        canvas.drawRoundRect(rectF, round, round, paint);
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;


    }
}
