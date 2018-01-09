package com.example.baseadapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.commit451.nativestackblur.NativeStackBlur;

import net.qiujuer.genius.blur.StackBlur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private List<ItemBean> itemBeanList;
//    private List<Integer> imageList = Arrays.asList(R.mipmap.a,R.mipmap.b,R.mipmap.c,
//            R.mipmap.d,R.mipmap.e,R.mipmap.f );
    private ListView listView;
    private MaskLayer maskLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the datasource
        itemBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            itemBeanList.add(new ItemBean(
                    R.mipmap.ic_launcher,
                    "I' m title" + i,
                    "I'm content" + i
            ));
        }


        listView = (findViewById(R.id.lv_main));
        maskLayer = findViewById(R.id.id_masklayer);
//        MyAdapterOne myAdapter = new MyAdapterOne(this, imageList);
        MyAdapter myAdapter = new MyAdapter(this, itemBeanList);
        listView.setAdapter(myAdapter);
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
        int radius = 15;
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


    public void startBlur(View view) {
        startActivity(new Intent(this, BlurListActivity.class));
    }
}
