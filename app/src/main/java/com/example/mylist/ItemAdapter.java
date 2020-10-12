package com.example.mylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private ArrayList<String> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView nomItem;

        public ItemViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            nomItem = view.findViewById(R.id.nomItem);
        }
    }

    public ItemAdapter(ArrayList<String> list) { this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v, listener);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.nomItem.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
