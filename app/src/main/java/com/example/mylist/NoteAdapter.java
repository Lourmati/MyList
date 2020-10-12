package com.example.mylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<Note> list;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNomListe;

        public NoteViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            tvNomListe = view.findViewById(R.id.nomListe);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public NoteAdapter(ArrayList<Note> list) { this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_note, parent, false);
        NoteViewHolder ivh = new NoteViewHolder(v, listener);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = list.get(position);
        holder.tvNomListe.setText(note.getNom());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
