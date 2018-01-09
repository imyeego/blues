package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.MyViewHolder>{

    private Context context;
    private List<String> list;
    private LayoutInflater layoutInflater;
    private List<Integer> mHeights;


    public StaggeredAdapter(List<String> list) {
        this.context = context;
        this.list = list;
//        this.layoutInflater = LayoutInflater.from(context);
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++){
            mHeights.add((int) (100+ Math.random() * 300));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.itemView.setLayoutParams(lp);

        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }


    }
}
