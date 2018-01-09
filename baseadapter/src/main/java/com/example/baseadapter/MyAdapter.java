package com.example.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class MyAdapter extends BaseAdapter {
    private List<ItemBean> mList;
    private LayoutInflater mInflater;
    public MyAdapter(Context context, List<ItemBean> list){
        mList = list;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        /* not recommended
        View = iview = mInflater.inflate(R.layout.item, null);
        ImageView imageView = iview.findViewById(R.id.iv_image);
        TextView title = iview.findViewById(R.id.tv_title);
        TextView content = iview.findViewById(R.id.tv_content);

        ItemBean itemBean = mList.get(i);

        imageView.setImageResource(itemBean.itemImageId);
        title.setText(itemBean.itemTitle);
        content.setText(itemBean.itemContent);
        */

        /*
        regular way
        if (view == null)
            view = mInflater.inflate(R.layout.item, null); //利用ListView缓存机制
        ImageView imageView = view.findViewById(R.id.iv_image);
        TextView title = view.findViewById(R.id.tv_title);
        TextView content = view.findViewById(R.id.tv_content);

        ItemBean itemBean = mList.get(i);

        imageView.setImageResource(itemBean.itemImageId);
        title.setText(itemBean.itemTitle);
        content.setText(itemBean.itemContent);
        */


        //advanced way
        class ViewHolder {
            private ImageView imageView;
            private TextView title;
            private TextView content;
        }
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item, null); //利用ListView缓存机制
            viewHolder.imageView = view.findViewById(R.id.iv_image);
            viewHolder.title = view.findViewById(R.id.tv_title);
            viewHolder.content = view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) view.getTag();
        ItemBean itemBean = mList.get(i);

        viewHolder.imageView.setImageResource(itemBean.itemImageId);
        viewHolder.title.setText(itemBean.itemTitle);
        viewHolder.content.setText(itemBean.itemContent);

        return view;
    }
}
