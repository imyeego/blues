package com.example.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/10/21 0021.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataModel> list;
    private Context context;
    private LayoutInflater layoutInflater;


    public MyAdapter(Context context, List list) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case DataModel.TYPE_ONE:
                return new TypeOneViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
            case DataModel.TYPE_TWO:
                return new TypeTwoViewHolder(layoutInflater.inflate(R.layout.item_2, parent, false));
            case DataModel.TYPE_THREE:
                return new TypeThreeViewHolder(layoutInflater.inflate(R.layout.item_3, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //int viewType = getItemViewType(position);
        ((TypeAbstractViewHolder)holder).bindHolder(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
