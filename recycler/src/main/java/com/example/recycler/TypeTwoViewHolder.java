package com.example.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class TypeTwoViewHolder extends TypeAbstractViewHolder {
    public ImageView imageView;
    public TextView textView;

    public TypeTwoViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_image_2);
        textView = itemView.findViewById(R.id.tv_title_2);
    }

    @Override
    public void bindHolder(DataModel model){
        imageView.setBackgroundColor(model.avatarcolor);
        textView.setText(model.name);

    }
}
