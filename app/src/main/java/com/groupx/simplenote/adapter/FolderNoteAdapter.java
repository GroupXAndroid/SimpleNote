package com.groupx.simplenote.adapter;

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
import com.groupx.simplenote.activity.FolderNoteActivity;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.entity.Note;

import java.util.List;

public class FolderNoteAdapter extends RecyclerView.Adapter<FolderNoteAdapter.FolderNoteViewHolder> {

    private List<Note> noteList;
    private FolderNoteActivity activity;

    public FolderNoteAdapter(List<Note> noteList, FolderNoteActivity activity) {
        this.noteList = noteList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FolderNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FolderNoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_note,
                parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull FolderNoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.setNote(note);
        holder.layoutFolderNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), CreateNoteActivity.class);
                intent.putExtra("folder", activity.currentFolder);
                intent.putExtra("mode", Const.NoteDetailActivityMode.EDIT);
                intent.putExtra("note", note);
                intent.putExtra("position", position);
                activity.startActivityForResult(intent, Const.NoteRequestCode.REQUEST_CODE_UPDATE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    static class FolderNoteViewHolder extends RecyclerView.ViewHolder{

        CardView layoutFolderNote;
        TextView textNoteTitle, textNoteSubTitle,textContent;

        public FolderNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutFolderNote = itemView.findViewById(R.id.layoutNoteContainer);
            textNoteTitle = itemView.findViewById(R.id.textNoteTitle);
            textNoteSubTitle = itemView.findViewById(R.id.textNoteSubTitle);
            textContent = itemView.findViewById(R.id.textContent);

        }

        void setNote(Note note){
            textNoteTitle.setText(note.getTitle());
            if (note.getColor() != null) {
                layoutFolderNote.setCardBackgroundColor(Color.parseColor(note.getColor()));
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
