package com.example.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class TypeOneViewHolder extends TypeAbstractViewHolder {
    public ImageView imageView;
    public TextView textViewOne;
    public TextView textViewTwo;

    public TypeOneViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_image);
        textViewOne = itemView.findViewById(R.id.tv_title);
        textViewTwo = itemView.findViewById(R.id.tv_content);
    }

    @Override
    public void bindHolder(DataModel model){
        textViewOne.setText(model.name);
        textViewTwo.setText(model.content);
        imageView.setBackgroundColor(model.avatarcolor);

    }
}
