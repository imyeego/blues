package com.example.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public abstract class TypeAbstractViewHolder extends RecyclerView.ViewHolder {
    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(DataModel model);
}
