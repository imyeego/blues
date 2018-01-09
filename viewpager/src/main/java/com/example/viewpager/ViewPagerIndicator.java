package com.example.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class ViewPagerIndicator extends LinearLayout {

    private Paint paint;

    private Path path;

    private int triangleWidth;

    private int triangleHeight;

    private static final float RADIO_TRIANGLE_WIDTH = 1/8F;

    private int initTranslationX;

    private int translationX = 0;

    private static final int DELFAULT_TAB_COUNT = 4;

    private static final int TEXT_COLOR_NORMAL = 0x77FFFFFF;

    private static final int TEXT_COLOR_HOGHLIGHT = 0xFFFFFFFF;

    private int visibleTabCount ;

    private ViewPager viewPager;

    public interface PageChangeListener{

        void onPageScrolled(int i, float v, int i1);
        void onPageSelected(int i);
        void onPageScrollStateChanged(int i);

    }

    private PageChangeListener mPageChangeListener;

    public void setPageChangeListener(PageChangeListener pageChangeListener){
        mPageChangeListener = pageChangeListener;
    }

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        visibleTabCount = ta.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, DELFAULT_TAB_COUNT);
        if (visibleTabCount < 0){
            visibleTabCount = DELFAULT_TAB_COUNT;
        }

        ta.recycle();


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setPathEffect(new CornerPathEffect(2));

    }

    public void setViewPager(ViewPager viewPager, int pos){
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                scroll(i, v);
                if (mPageChangeListener != null){
                    mPageChangeListener.onPageScrolled(i, v, i1);
                }
            }

            @Override
            public void onPageSelected(int i) {
                if (mPageChangeListener != null){
                    mPageChangeListener.onPageSelected(i);
                }
                highlightTextView(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (mPageChangeListener != null){
                    mPageChangeListener.onPageScrollStateChanged(i);
                }

            }
        });
        this.viewPager.setCurrentItem(pos);
        highlightTextView(pos);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count <= 0) return;

//        LinearLayout.LayoutParams lp = null;
//
//        View rootView = getRootView();
//        lp = (LayoutParams) getLayoutParams();
//        lp.height = dp2px(48) + getStatusHeight();
//        rootView.setLayoutParams(lp);

        for (int i = 0; i < count; i ++){
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / visibleTabCount;
            lp.setMargins(0, getStatusHeight(), 0, 0);
            view.setLayoutParams(lp);


        }
        setItemClickEvent();

    }

    /**
     * 获取状态栏
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

    /**
    获取屏幕宽度
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(initTranslationX + translationX, getHeight());
        canvas.drawPath(path, paint);

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        triangleWidth = (int) (w / visibleTabCount * RADIO_TRIANGLE_WIDTH);
        initTranslationX = (w / visibleTabCount - triangleWidth) / 2;

        initTriangle();
    }

    private void initTriangle() {

        triangleHeight = (int) (triangleWidth / 2 * Math.tan(Math.PI / 5));

        path = new Path();
        path.moveTo(0, 0);
        path.lineTo(triangleWidth, 0);
        path.lineTo(triangleWidth / 2, -triangleHeight);
        path.close();

    }

    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / visibleTabCount;
        translationX = (int) (tabWidth * (position + offset));

        if (position >= (visibleTabCount -2) && position < getChildCount() - 2 && offset > 0
                && getChildCount() > visibleTabCount){
            if (visibleTabCount != 1) {
                this.scrollTo((int) ((position - (visibleTabCount - 2))
                        * tabWidth + offset * tabWidth),0);
            }else{
                this.scrollTo((int) (position * tabWidth + offset * tabWidth),0);
            }
        }

        invalidate();
    }

    /**
     * 重置TAB文本
     */
    protected void resetTextViewColor(){
        for (int i = 0; i < getChildCount(); i ++){
            View view = getChildAt(i);
            if (view instanceof TextView){
                ((TextView) view).setTextColor(TEXT_COLOR_NORMAL);
                TextPaint tp = ((TextView) view).getPaint();
                tp.setFakeBoldText(false);
            }
        }
    }

    /**
     * 高亮某个tab的文本
     * @param pos
     */
    protected void highlightTextView(int pos){
        resetTextViewColor();

        View view = getChildAt(pos);
        if (view instanceof TextView){
            ((TextView) view).setTextColor(TEXT_COLOR_HOGHLIGHT);
            TextPaint tp = ((TextView) view).getPaint();
            tp.setFakeBoldText(true);

        }
    }

    private void setItemClickEvent(){
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i ++){
            final int j = i;

            View view = getChildAt(j);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(j);
                }
            });
        }


    }

    private int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getResources().getDisplayMetrics());
    }
}
