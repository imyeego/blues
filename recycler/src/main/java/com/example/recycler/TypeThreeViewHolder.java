package com.example.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class TypeThreeViewHolder extends TypeAbstractViewHolder {

    public ImageView imageView;
    public TextView textViewOne;
    public TextView textViewTwo;
    public TypeThreeViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_image_3);
        textViewOne = itemView.findViewById(R.id.tv_title_3);
        textViewTwo = itemView.findViewById(R.id.tv_content_3);
    }


    @Override
    public void bindHolder(DataModel model){
        imageView.setBackgroundColor(model.avatarcolor);
        textViewOne.setText(model.name);
        textViewTwo.setText(model.content);

    }
}
