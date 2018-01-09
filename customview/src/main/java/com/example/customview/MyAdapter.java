package com.example.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class MyAdapter extends BaseAdapter {

    private List<String> mlist = new ArrayList<>();
    private LayoutInflater mInflater;

    public MyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null)
            view = mInflater.inflate(R.layout.item, null); //利用ListView缓存机制

        CustomTextView customTextView = view.findViewById(R.id.lv_ctv);
        customTextView.setText(mlist.get(position));

        return view;
    }

    public void setDatas(List<String> list){
        this.mlist = list;
    }
}
