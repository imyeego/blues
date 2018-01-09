package com.example.baseadapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/11/12 0012.
 */

public class ImageItemView extends LinearLayout {




    public ImageItemView(Context context) {
        this(context,null);
    }

    public ImageItemView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);



    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();



    }

}
