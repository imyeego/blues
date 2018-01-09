package com.example.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("收藏", "短信", "推荐","科技", "娱乐", "社会","经济");
    private List<VpFragment> contents = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPagerIndicator.setViewPager(viewPager, 0);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//                viewPagerIndicator.scroll(i, v);
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                viewPagerIndicator.highlightTextView(i);
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });

    }

    private void initData() {
        for (String title: titles){
            VpFragment vpFragment = VpFragment.newInstance(title);
            contents.add(vpFragment);
        }

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return contents.get(i);
            }

            @Override
            public int getCount() {
                return contents.size();
            }
        };

    }

    private void initView() {
        viewPager = findViewById(R.id.id_viewpager);
        viewPagerIndicator = findViewById(R.id.id_indicator);
    }
}
