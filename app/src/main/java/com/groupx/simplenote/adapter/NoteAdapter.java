package com.groupx.simplenote.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Note> notes;
    private final NoteListener noteListener;

    public NoteAdapter(List<Note> notes, NoteListener noteListener) {
        this.notes = notes;
        this.noteListener = noteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_note,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setNote(notes.get(position));
        holder.layoutNoteContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteListener.onNoteClicked(notes.get(position), position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutNoteContainer;
        TextView textTitle, textSubtitle;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutNoteContainer = itemView.findViewById(R.id.layoutNote);
            textTitle = itemView.findViewById(R.id.textNoteTitle);
            textSubtitle = itemView.findViewById(R.id.textNoteSubtitle);

        }

        void setNote(Note note) {
            textTitle.setText(note.getTitle());
            if (note.getColor() != null) {
                layoutNoteContainer.setBackgroundColor(Color.parseColor(note.getColor()));
            }
            if (note.getSubTitle().trim().isEmpty()) {
                textSubtitle.setVisibility(View.GONE);
            } else {
                textSubtitle.setText(note.getSubTitle());
            }
        }
    }
}
