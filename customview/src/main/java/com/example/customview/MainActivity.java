package com.example.customview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private CustomTextView customTextView;
    private List<String> list;
    private MyEditText editText;
    private ListView listView;
    private MyAdapter myAdapter;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        customTextView = findViewById(R.id.custom_tv);

        send = findViewById(R.id.bt);
        editText = findViewById(R.id.custom_et);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout footer = (LinearLayout) inflator.inflate(R.layout.footer, null);

        initDatas();
        listView = findViewById(R.id.id_lv);
        myAdapter = new MyAdapter(this);
        myAdapter.setDatas(list);
        listView.addFooterView(footer);
//        listView.setStackFromBottom(true);
        listView.setAdapter(myAdapter);
        listView.setSelection(myAdapter.getCount() - 1);


        send.setOnClickListener(this);
        listView.setOnItemClickListener(this);

    }

    private void initDatas() {
        list = new ArrayList<>();
        for (int i = 0; i < 8; i ++){
            list.add("halo" + i);
        }
    }


    @Override
    public void onClick(View v) {
        String content = editText.getText().toString().trim();
        switch (v.getId()){
            case R.id.bt:
                if(content.length() != 0) {
                    list.add(content);
                    myAdapter.notifyDataSetChanged();
                    int offset = listView.getChildAt(1).getHeight();
                    listView.smoothScrollByOffset(offset);
//                    listView.setSelection(myAdapter.getCount() - 1);
//                    customTextView.setText(content);
                    editText.getText().clear();
                }
                else{
                    Toast.makeText(MainActivity.this,"content is null", Toast.LENGTH_SHORT).show();
                }


            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.clearFocus();
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
