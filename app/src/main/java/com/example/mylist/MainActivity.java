package com.example.mylist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseHelper mDatabaseHelper;

    ConstraintLayout layout;
    private FloatingActionButton buttonAdd;
    private ArrayList<Note> list = new ArrayList<Note>();
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int clientTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main_layout);

        mDatabaseHelper = new DatabaseHelper(this);

        populateList();
        buildAddButton();
        buildRecycleView();
        buildItemTouchHelper();
    }

    private void buildItemTouchHelper() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int direction) {
                notifyDeleteSelected(target.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    private void buildAddButton() {
        buttonAdd = this.findViewById(R.id.addListButton);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityList(0, "add");
            }
        });
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
        String name = list.get(position).getNom();
        mDatabaseHelper.deleteData(getItemID(name), name);
        list.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
    }

    private void notifyItemSelected(int position) {
        toastMessage(list.get(position).getNom()+ " " + getResources().getString(R.string.selected_list));
        openActivityList(position, "edit");
    }

    private void openActivityList(int position, String function) {
        Intent intent = new Intent(getApplicationContext(),ActivityList.class);

        if(function.equals("edit")){
            clientTmp = position;
            intent.putExtra("note", list.get(position));
            startActivityForResult(intent, 1);

        } else if (function.equals("add")){
            intent.putExtra("note", new Note(getResources().getString(R.string.new_list),new ArrayList<String>() ));
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                    Note returnedNote =  data.getParcelableExtra("returnNote");
                    UpdateData(returnedNote.getNom(), ArrayToString(returnedNote.getListe()), list.get(clientTmp).getNom());
                    list.get(clientTmp).setNom(returnedNote.getNom());
                    list.get(clientTmp).setListe(returnedNote.getListe());

            } else if (requestCode == 2) {
                    Note returnedNote =  data.getParcelableExtra("returnNote");
                    list.add(0, returnedNote);
                    AddData(returnedNote.getNom(), ArrayToString(returnedNote.getListe()));
            }
        }
        adapter.notifyItemInserted(adapter.getItemCount()-1);
        adapter.notifyDataSetChanged();
    }

    private void UpdateData(String newName, String newList, String oldName) {
        int itemID = getItemID(oldName);
        mDatabaseHelper.updateData(newName, newList, itemID, oldName);
    }

    public void AddData(String newName, String newList){
        boolean insertData = mDatabaseHelper.addData(newName,newList);

        if (insertData) {
            toastMessage(getResources().getString(R.string.data_inserted));
        } else {
            toastMessage(getResources().getString(R.string.data_notinserted));
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void populateList() {
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()){
            String name = data.getString(1);
            ArrayList<String> liste = new ArrayList<String>(Arrays.asList(data.getString(2).split(" ")));
            list.add(new Note(name, liste));
        }
    }

    private int getItemID(String name){
        Cursor data = mDatabaseHelper.getItemID(name);
        int itemID = -1;
        while(data.moveToNext()){
            itemID = data.getInt(0);
        }
        if(itemID > -1){
            Log.d(TAG, "onItemClick: The ID is: " + itemID);
        }
        else{
            Log.d(TAG, "No ID associated with that name");
        }

        return itemID;
    }

    private String ArrayToString(ArrayList<String> listS){
        return TextUtils.join(" ", listS);
    }

}
