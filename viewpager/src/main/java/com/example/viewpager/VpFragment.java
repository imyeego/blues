package com.example.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class VpFragment extends Fragment {

    private String title;
    private static final String BUNDLE_TITLE = "title";
    private TextView tv;
    private ImageView iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_view, null);


        Bundle bundle = getArguments();
        if(bundle != null)
            title = bundle.getString(BUNDLE_TITLE);
        tv = view.findViewById(R.id.tv_00);
        iv = view.findViewById(R.id.iv_image00);
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        iv.setImageResource(R.drawable.iv_00);

        return view;
    }

    public static VpFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);

        VpFragment fragment = new VpFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
