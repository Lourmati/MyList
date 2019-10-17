package com.example.mylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {



        public ItemViewHolder(View view, final OnItemClickListener listener) {
            super(view);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // vérifier que le listener n'est pas null
                    if (listener != null) {
                        int position = getAdapterPosition();
                        // la position est valide?
                        if (position != RecyclerView.NO_POSITION) {
                            //listener.onItemClick(position);
                        }
                    }
                }
            });

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
        String item = list.get(position);
        //holder.tvNom.setText(client.getNom());
        //holder.tvPrenom.setText(client.getPrenom());
        //holder.ivPhoto.setImageResource(client.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
