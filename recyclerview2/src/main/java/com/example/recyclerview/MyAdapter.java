package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private Context context;
    private List<String> list;
//    private LayoutInflater layoutInflater;
    public interface OnItemClickLisener{
        void onItemClick(View view, int pos);
        void onItemLongClick(View view, int pos);
    }
    private OnItemClickLisener onItemClickLisener;

    protected void setOnItemClickLister(OnItemClickLisener onItemClickLister){
        this.onItemClickLisener = onItemClickLister;
    }


    public MyAdapter(List<String> list) {
        this.context = context;
        this.list = list;
//        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textView.setText(list.get(position));
        if (onItemClickLisener!= null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int layoutPosition = holder.getLayoutPosition();
                onItemClickLisener.onItemClick(holder.itemView, layoutPosition);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    onItemClickLisener.onItemLongClick(holder.itemView, layoutPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(int pos){
        list.add(pos, "Insert One");
        notifyItemInserted(pos);

    }

    public void delete(int pos){
        list.remove(pos);
        notifyItemRemoved(pos);
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }


    }
}
