package com.example.baseadapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import net.qiujuer.genius.blur.StackBlur;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/11/12 0012.
 */

public class BlurListActivity extends FragmentActivity {

    private ListView listView;
    private MaskLayer maskLayer;

    private List<Integer> imageList = Arrays.asList(R.drawable.a,R.drawable.b,R.drawable.c,
            R.drawable.d,R.drawable.e,R.drawable.f );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_item);
        listView = findViewById(R.id.lv_image);
        maskLayer = findViewById(R.id.id_image_masklayer);
        MyAdapterOne adapterOne = new MyAdapterOne(this, imageList);

        listView.setAdapter(adapterOne);
        applyBlur();


    }

    private void applyBlur() {
        listView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                listView.getViewTreeObserver().removeOnPreDrawListener(this);
                listView.buildDrawingCache();

                Bitmap bmp = listView.getDrawingCache();
                blur(bmp,maskLayer);

                return true;
            }


        });
        listView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                listView.buildDrawingCache();
                Bitmap bmp = listView.getDrawingCache();
                blur(bmp, maskLayer);
            }
        });
    }
    private void blur(Bitmap bmp, View view) {
        int radius = 100;
//        int scaleFactor = 4;

        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(overlay);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bmp, 0, 0, paint);

//        RenderScript rs = RenderScript.create(this);
//
//        Allocation overlayAlloc = Allocation.createFromBitmap(
//                rs, overlay);
//
//        Allocation overlayAllocOne = Allocation.createFromBitmap(
//                rs, overlay);
//
//        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
//                rs, overlayAlloc.getElement());
//
//        ScriptIntrinsicBlur blurOne = ScriptIntrinsicBlur.create(
//                rs, overlayAlloc.getElement());
//
//        blur.setInput(overlayAlloc);
//        blurOne.setInput(overlayAllocOne);
//
//        blur.setRadius(radius);
//        blurOne.setRadius(radius);
//
//        blur.forEach(overlayAlloc);
//        blurOne.forEach(overlayAllocOne);
//
//        overlayAlloc.copyTo(overlay);
//        overlayAllocOne.copyTo(overlay);
        overlay = StackBlur.blurNatively(overlay, radius, false);

        view.setBackground(new BitmapDrawable(
                getResources(), overlay));

//        rs.destroy();

    }

}
