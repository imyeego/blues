package com.example.images;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ImagePageActivity extends FragmentActivity {

    private static final String STATE_POSITION = "STATE_POSITION";

    private ViewPager viewPager;
    private int pagePosition;
    private ArrayList<String> urls;
    private TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);


        setContentView(R.layout.image_detail);
        Fade fade = new Fade();
        fade.setDuration(300);
        getWindow().setEnterTransition(fade);
        pagePosition = getIntent().getIntExtra("position", 0);
        urls = getIntent().getStringArrayListExtra("urls");

        viewPager = findViewById(R.id.vp);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);

        viewPager.setAdapter(mAdapter);
        textView = findViewById(R.id.indicator);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                int currentposition = arg0 + 1;
                CharSequence text = currentposition + "/" + viewPager.getAdapter().getCount();
                textView.setText(text);
            }
        });

        if (savedInstanceState != null){
            pagePosition = savedInstanceState.getInt(STATE_POSITION);
        }
        viewPager.setCurrentItem(pagePosition);
        CharSequence text = (pagePosition+1) + "/" + viewPager.getAdapter().getCount();
        textView.setText(text);




    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(STATE_POSITION, viewPager.getCurrentItem());

    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }

    }
}
