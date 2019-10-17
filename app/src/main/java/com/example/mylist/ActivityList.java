package com.example.mylist;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {
    ConstraintLayout layout;
    private ArrayList<String> list;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        layout = findViewById(R.id.activitylist_layout);
        buildRecycleView();
    }


    private void buildRecycleView() {
        recyclerView = findViewById(R.id.recyclerViewNote);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ItemAdapter(list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                notifyItemSelected(position);
            }

            @Override
            public void onDeleteClick(int position) {
                notifyDeleteSelected(position);
            }
        });
    }

    private void notifyDeleteSelected(int position) {
    }

    private void notifyItemSelected(int position) {
        Log.v("RecyclerView", "" + position + " a été sélectionné: " + list.get(position));


    }

}
