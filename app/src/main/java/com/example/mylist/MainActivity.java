package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout layout;
    private ArrayList<Note> list = new ArrayList<Note>();
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main_layout);

        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("sss");
        list1.add("qwdqwe");
        list1.add("asdassd");
        Note note1 = new Note("liste 1", list1);
        list.add(note1);
        buildRecycleView();
    }

    private void buildRecycleView() {
        recyclerView = findViewById(R.id.recyclerViewNote);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NoteAdapter(list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
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
        Log.v("RecyclerView", "" + position + " a été sélectionné: " + list.get(position).getNom());


    }


}
