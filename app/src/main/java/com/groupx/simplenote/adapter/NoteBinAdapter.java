package com.groupx.simplenote.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.List;

public class NoteBinAdapter extends RecyclerView.Adapter<NoteBinAdapter.NoteViewHolder> {

    private final List<Note> notes;
    private final NoteListener noteListener;

    public NoteBinAdapter(List<Note> notes, NoteListener noteListener) {
        this.notes = notes;
        this.noteListener = noteListener;
    }

    @NonNull
    @Override
    public NoteBinAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteBinAdapter.NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_note_bin,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteBinAdapter.NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        ConstraintLayout layoutNoteContainer;
        TextView textTitle, textSubtitle;
        ImageView imageViewRestore, imageViewDelete;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutNoteContainer = itemView.findViewById(R.id.layoutNote);
            textTitle = itemView.findViewById(R.id.txtTitle);
            textSubtitle = itemView.findViewById(R.id.txtSubtitle);
            imageViewRestore = itemView.findViewById(R.id.imageViewRestore);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);

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

            imageViewRestore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    note.setStatusKey(Const.NoteStatus.NORMAL);
                    NoteDatabase.getSNoteDatabase(view.getContext())
                            .noteDao().update(note);

                    Toast.makeText(view.getContext(), "Restored", Toast.LENGTH_LONG).show();
                }
            });

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteDatabase.getSNoteDatabase(view.getContext())
                            .noteDao().deleteNote(note);
                    Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
