package com.example.operationsunlight.modules.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.operationsunlight.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private OnNoteListener onNoteListener;
    ArrayList<Note> notes;

    public NoteAdapter(Context context, OnNoteListener onNoteListener, ArrayList<Note> notes) {
        this.context = context;
        this.onNoteListener = onNoteListener;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_note, parent, false);
        return new ViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.position = position;
        holder.note_textview.setText(note.getNote());
        holder.date_textview.setText(note.getDate_created().toString());
    }

    @Override
    public int getItemCount() { return notes.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private int position;
        OnNoteListener onNoteListener;

        TextView note_textview, date_textview;
        Button delete_note_button;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            note_textview = itemView.findViewById(R.id.note_textview);
            date_textview = itemView.findViewById(R.id.date_textview);
            delete_note_button = itemView.findViewById(R.id.delete_note_button);
            delete_note_button.setOnClickListener(this::onClick);
            layout = itemView.findViewById(R.id.layout);
            layout.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_note_button: onNoteListener.onNoteDelete(position); break;
                case R.id.layout: onNoteListener.onNoteUpdate(position); break;
            }
        }
    }
}
