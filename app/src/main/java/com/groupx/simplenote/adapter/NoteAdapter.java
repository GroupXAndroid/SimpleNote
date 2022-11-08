package com.groupx.simplenote.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.CreateNoteActivity;
import com.groupx.simplenote.activity.MainActivity;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Note> notes;
    private final MainActivity activity;

    public NoteAdapter(List<Note> notes, MainActivity activity) {
        this.notes = notes;
        this.activity = activity;
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
        Note note = notes.get(position);
        holder.setNote(notes.get(position));
        holder.layoutNoteContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), CreateNoteActivity.class);
                intent.putExtra("mode", Const.NoteDetailActivityMode.EDIT);
                intent.putExtra("note", note);
                intent.putExtra("position", position);
                activity.startActivityForResult(intent, Const.NoteRequestCode.REQUEST_CODE_UPDATE);
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
        CardView layoutNoteContainer;
        TextView textNoteTitle, textNoteSubTitle,textContent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutNoteContainer = itemView.findViewById(R.id.layoutNoteContainer);
            textNoteTitle = itemView.findViewById(R.id.textNoteTitle);
            textNoteSubTitle = itemView.findViewById(R.id.textNoteSubTitle);
            textContent = itemView.findViewById(R.id.textContent);

        }

        void setNote(Note note){
            textNoteTitle.setText(note.getTitle());
            if (note.getColor() != null) {
                layoutNoteContainer.setCardBackgroundColor(Color.parseColor(note.getColor()));
            }
            if (note.getSubTitle().trim().isEmpty()) {
                textNoteSubTitle.setVisibility(View.GONE);
            } else {
                textNoteSubTitle.setText(note.getSubTitle());
            }
            if(note.getNote().trim().isEmpty()){
                textContent.setVisibility(View.GONE);
            }else {
                int maxsize = note.getNote().length();
                if(maxsize >= 50){
                    maxsize = 50;
                }
                textContent.setText(note.getNote().substring(0, maxsize));
            }
        }
    }
}
