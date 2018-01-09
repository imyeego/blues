package com.example.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> imageUrls;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.gridview_item, null);

        ImageView imageView = convertView.findViewById(R.id.grid_iv);

        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .bitmapConfig(Bitmap.Config.RGB_565)//
                .build();
        ImageLoader.getInstance().displayImage((String) getItem(position), imageView, options);


        return convertView;
    }
}
