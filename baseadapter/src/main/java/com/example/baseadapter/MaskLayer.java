package com.example.baseadapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/11/12 0012.
 */

public class MaskLayer extends ImageView {
    public MaskLayer(Context context) {
        this(context, null);
    }

    public MaskLayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }


}
