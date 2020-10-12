package com.example.mylist;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ActivityList extends AppCompatActivity {
    private static final String TAG = "ActivityList";
    DatabaseHelper mDatabaseHelper;
    ConstraintLayout layout;
    private ArrayList<String> list;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText nomListe;
    private FloatingActionButton addItem;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        layout = findViewById(R.id.activitylist_layout);
        nomListe = findViewById(R.id.titreListe);
        buildToolBar();
        intentManagment();
        buildRecycleView();
        buildItemTouchHelper();
    }

    private void buildToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addItem = findViewById(R.id.addItemButton);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertDialog();
            }
        });
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

    private void buildAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_item,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_add = (Button) dialogView.findViewById(R.id.dialog_add_btn);
        Button btn_cancel = (Button) dialogView.findViewById(R.id.dialog_cancel_btn);
        final EditText et_add = (EditText) dialogView.findViewById(R.id.et_add);
        final AlertDialog dialog = builder.create();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addListItem(et_add.getText().toString());
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void intentManagment() {
        intent = getIntent();
        Note note = intent.getParcelableExtra("note");
        nomListe.setText(note.getNom());
        list = note.getListe();
    }

    private void validateList() {
        if(!nomListe.getText().toString().trim().isEmpty()){
            Note note;
            ArrayList<String> listV = new ArrayList<String>();

            for(int i=0;i<recyclerView.getAdapter().getItemCount();i++)
            {
                TextView e = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.nomItem);
                listV.add(e.getText().toString());
            }

            note = new Note (nomListe.getText().toString(), listV);
            intent.putExtra("returnNote", note);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void addListItem(String newItem) {
        list.add(0,newItem);
        adapter.notifyItemInserted(adapter.getItemCount()-1);
        adapter.notifyDataSetChanged();
    }

    private void buildRecycleView() {
        recyclerView = findViewById(R.id.recyclerViewItem);
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
        list.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
    }

    private void notifyItemSelected(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SAVE:
                validateList();
                return true;
            case R.id.SEND:
                sendMail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

}

    private void sendMail() {
        String message = "";
        String name = nomListe.getText().toString();
        for(int i=0;i<recyclerView.getAdapter().getItemCount();i++)
        {
            TextView e = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.nomItem);
            message += e.getText().toString() + "\n";
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "list@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "List : " + name);
        intent.putExtra(Intent.EXTRA_TEXT, name + "\n" + message);
        intent.setData(Uri.parse("mailto:" + "list@gmail.com"));
        this.startActivity(Intent.createChooser(intent, "Send e-mail to"));
    }


}