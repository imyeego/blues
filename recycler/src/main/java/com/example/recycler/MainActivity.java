package com.example.recycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<DataModel> list;
    int colors[] = {android.R.color.holo_blue_bright,
            android.R.color.holo_red_dark,
            android.R.color.holo_green_dark};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        recyclerView = findViewById(R.id.id_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(this, list);

        recyclerView.setAdapter(myAdapter);


    }

    private void initData() {
        list = new ArrayList<>();

        for (int i = 0; i < 20; i ++){
            DataModel dataModel = new DataModel();
            int type = (int) (Math.random() * 3 + 1);

            dataModel.type = type;
            dataModel.name = "name: " + i;
            dataModel.content = "content: " + i;
            dataModel.avatarcolor = colors[type - 1];
            dataModel.contentcolor = colors[type % 3];

            list.add(dataModel);
        }
        //myAdapter.notifyDataSetChanged();

    }
}
