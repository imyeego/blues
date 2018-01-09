package com.example.baseadapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class TitleBar extends LinearLayout {

    private int statusHeight = getStatusHeight();

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = getChildAt(0);
        LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
        int topMagin = statusHeight + (30>>1);
        lp.setMargins(60, topMagin, 0, 0);
        view.setLayoutParams(lp);

        view = getChildAt(1);
        lp = (LayoutParams) view.getLayoutParams();
//        int topMagin = statusHeight + (30>>1);
        lp.setMargins(0, topMagin-15, 10, 0);
        view.setLayoutParams(lp);
    }

    /**
     * 获取状态栏高度
     * @return
     */
    private int getStatusHeight() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight1;
    }
}
