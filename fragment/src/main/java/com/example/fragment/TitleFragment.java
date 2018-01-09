package com.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/14 0014.
 */

public class TitleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title, null);
        RelativeLayout layout = view.findViewById(R.id.rl_title);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"我是标题栏",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
