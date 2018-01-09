package com.example.asyctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;

import net.qiujuer.genius.blur.StackBlur;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class ImageTest extends FragmentActivity {

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private MaskLayer maskLayer;
    private static String URL =
            "https://wx1.sinaimg.cn/mw690/6a5d5455ly1flharvnu7wj20rs0rsq6a.jpg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images);

        mImageView = findViewById(R.id.image);
        mProgressBar = findViewById(R.id.progressbar);
        maskLayer = findViewById(R.id.id_masklayer);


        new MyAsyncTask().execute(URL);
    }

    private void applyBlur() {
        mImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                mImageView.buildDrawingCache();

                Bitmap bmp = mImageView.getDrawingCache();
                blur(bmp,maskLayer);

                return true;
            }


        });
        mImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mImageView.buildDrawingCache();

                Bitmap bmp = mImageView.getDrawingCache();
                blur(bmp, maskLayer);
            }
        });
    }

    private void blur(Bitmap bmp, View view) {
        int radius = 70;

        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);

        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bmp, 0, 0, null);

//        RenderScript rs = RenderScript.create(this);
//
//        Allocation overlayAlloc = Allocation.createFromBitmap(
//                rs, overlay);
//
//        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
//                rs, overlayAlloc.getElement());
//
//        blur.setInput(overlayAlloc);
//
//        blur.setRadius(radius);
//
//        blur.forEach(overlayAlloc);
//
//        overlayAlloc.copyTo(overlay);
        overlay = StackBlur.blurNatively(overlay, radius, false);

        view.setBackground(new BitmapDrawable(
                getResources(), overlay));

//        rs.destroy();

    }

    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressBar.setVisibility(View.GONE);
            mImageView.setImageBitmap(bitmap);
            applyBlur();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            URLConnection urlConnection;
            InputStream is;
            try {
                urlConnection = new URL(url).openConnection();
                is = urlConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                Thread.sleep(1000);

                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }
}
