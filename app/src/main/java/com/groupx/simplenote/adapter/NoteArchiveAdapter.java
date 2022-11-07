package com.groupx.simplenote.adapter;

import android.annotation.SuppressLint;
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

public class NoteArchiveAdapter extends RecyclerView.Adapter<NoteArchiveAdapter.NoteViewHolder> {

    private final List<Note> notes;
    private final NoteListener noteListener;

    public NoteArchiveAdapter(List<Note> notes, NoteListener noteListener) {
        this.notes = notes;
        this.noteListener = noteListener;
    }

    @NonNull
    @Override
    public NoteArchiveAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteArchiveAdapter.NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_note_archive,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteArchiveAdapter.NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        TextView textTitle, textSubtitle, txtReminderTime;
        ImageView imgUnarchive;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutNoteContainer = itemView.findViewById(R.id.layoutNote);
            textTitle = itemView.findViewById(R.id.txtTitle);
            textSubtitle = itemView.findViewById(R.id.txtSubtitle);
            imgUnarchive = itemView.findViewById(R.id.imageViewUnarchive);
            txtReminderTime = itemView.findViewById(R.id.txtReminderTime);
        }

        void setNote(@NonNull Note note) {
            textTitle.setText(note.getTitle());
            if (note.getColor() != null) {
                layoutNoteContainer.setBackgroundColor(Color.parseColor(note.getColor()));
            }
            if (note.getSubTitle().trim().isEmpty()) {
                textSubtitle.setVisibility(View.GONE);
            } else {
                textSubtitle.setText(note.getSubTitle());
            }

            if (note.getReminderTime() == null) {
                txtReminderTime.setVisibility(View.GONE);
            } else {
                txtReminderTime.setText(note.getSubTitle());
            }

            imgUnarchive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (note.getStatusKey() == Const.NoteStatus.FAVORITE) {
                        note.setStatusKey(Const.NoteStatus.NORMAL);
                        NoteDatabase.getSNoteDatabase(view.getContext())
                                .noteDao().update(note);
                    } else {
                        note.setStatusKey(Const.NoteStatus.FAVORITE);
                        NoteDatabase.getSNoteDatabase(view.getContext())
                                .noteDao().update(note);
                    }
                    Toast.makeText(view.getContext(), "Note Updated", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
