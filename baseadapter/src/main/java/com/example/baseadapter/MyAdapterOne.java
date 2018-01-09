package com.example.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class MyAdapterOne extends BaseAdapter {
    private List<Integer> mList;
    private LayoutInflater mInflater;

    public MyAdapterOne(Context context, List<Integer> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null)
            view = mInflater.inflate(R.layout.image, null); //利用ListView缓存机制
        RoundImageView imageView = view.findViewById(R.id.id_image);

        imageView.setImageResource(mList.get(position));

        return view;
    }
}
