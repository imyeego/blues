package com.example.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> list;
    private StaggeredAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initView();
        adapter = new StaggeredAdapter(list);
        recyclerView.setAdapter(adapter);
//        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    private void initView() {
        recyclerView = findViewById(R.id.id_recyclerview);
    }

    private void initDatas() {
        list = new ArrayList<String>();
        for (int i = 'A'; i <= 'z'; i++){
            list.add(" " + (char)i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


//        switch (id){
//            case R.id.action_listview:
//                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//                break;
//            case R.id.action_gridview:
//                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//                break;
//            case R.id.action_hor_gridview:
//                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
//                        StaggeredGridLayoutManager.HORIZONTAL));
//                break;
//            case R.id.action_staggered:
//                break;
//            default:
//                break;
//
//        }

        return super.onOptionsItemSelected(item);
    }


}
