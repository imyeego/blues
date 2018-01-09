package com.example.images;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class ListItemAdapter extends BaseAdapter {

    private Context context;
    private List<ItemEntity> list;
    private LayoutInflater inflater;

    public ListItemAdapter(Context context, List<ItemEntity> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        ItemEntity itemEntity = list.get(position);

        TextView username = convertView.findViewById(R.id.username);
        username.setText(itemEntity.getName());

        TextView content = convertView.findViewById(R.id.content);
        content.setText(itemEntity.getContent());


        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .showImageOnLoading(R.drawable.ic_launcher) // 加载中显示的默认图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置加载失败的默认图片
                .cacheInMemory(true) // 内存缓存
                .cacheOnDisk(true) // sdcard缓存
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
                .build();//
        ImageView imageView = convertView.findViewById(R.id.avatar);
        ImageLoader.getInstance().displayImage(itemEntity.getAvatar(), imageView, options);

        MyGridView gridView = convertView.findViewById(R.id.gridview);
        final ArrayList<String> imageUrls = itemEntity.getImages();
        if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
            gridView.setVisibility(View.GONE);
        } else {
            gridView.setAdapter(new GridViewAdapter(context,
                    imageUrls));
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, ImagePageActivity.class);
                ImageView mImageView = view.findViewById(R.id.grid_iv);
                intent.putExtra("urls", imageUrls);
                intent.putExtra("position", position);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                        mImageView, "shareName");
                context.startActivity(intent, optionsCompat.toBundle());
            }
        });

        return convertView;
    }


}
