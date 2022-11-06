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
import com.groupx.simplenote.activity.ShareWithMeActivity;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.dto.NoteShareWithMeDTO;

import java.util.List;

public class SwmNoteAdapter extends RecyclerView.Adapter<SwmNoteAdapter.SwmViewHolder> {

    private List<NoteShareWithMeDTO> noteSwmList;
    private ShareWithMeActivity activity;

    public SwmNoteAdapter(List<NoteShareWithMeDTO> noteSwmList, ShareWithMeActivity activity) {
        this.noteSwmList = noteSwmList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SwmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SwmViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_container_share_with_me_note,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SwmViewHolder holder, int position) {
        NoteShareWithMeDTO noteShare = noteSwmList.get(position);
        holder.setSwmNote(noteShare);
        holder.cardSwmNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), CreateNoteActivity.class);
                if(noteShare.getPermissions().equals(Const.StatusPermission.VIEW.toString())){
                    intent.putExtra("mode", Const.NoteDetailActivityMode.VIEW);
                }else if(noteShare.getPermissions().equals(Const.StatusPermission.EDIT.toString())){
                    intent.putExtra("mode", Const.NoteDetailActivityMode.EDIT);
                }
                intent.putExtra("note", noteShare.getNote());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteSwmList.size();
    }

    static class SwmViewHolder extends RecyclerView.ViewHolder {

        TextView textNoteSwmTitle, textNoteSwmSubtitle, textAccountSwm, textDateEdited;
        CardView cardSwmNote;

        SwmViewHolder(@NonNull View itemView) {
            super(itemView);
            cardSwmNote = itemView.findViewById(R.id.cardSwmNote);
            textNoteSwmTitle = itemView.findViewById(R.id.textNoteSwmTitle);
            textNoteSwmSubtitle = itemView.findViewById(R.id.textNoteSwmSubTitle);
            textAccountSwm = itemView.findViewById(R.id.textAccountSwm);
            textDateEdited = itemView.findViewById(R.id.textDateSwmEdited);
        }

        void setSwmNote(NoteShareWithMeDTO noteSwm) {
            textNoteSwmTitle.setText(noteSwm.getNote().getTitle());

            if (noteSwm.getNote().getSubTitle() == null) {
                textNoteSwmSubtitle.setVisibility(View.GONE);
            } else {
                textNoteSwmSubtitle.setVisibility(View.VISIBLE);
                textNoteSwmSubtitle.setText(noteSwm.getNote().getSubTitle());
            }
            if (noteSwm.getAccount().getFullName() == null) {
                textAccountSwm.setVisibility(View.INVISIBLE);
            } else {
                textAccountSwm.setText(noteSwm.getAccount().getFullName());
            }
            String time = Utils.DateTimeToString(noteSwm.getNote().getLastUpdate());
            if (time == null) {
                textDateEdited.setVisibility(View.INVISIBLE);
            } else {
                textDateEdited.setText(time);
            }
            if (noteSwm.getNote().getColor() != null && !noteSwm.getNote().getColor().trim().isEmpty()) {
                cardSwmNote.setCardBackgroundColor(
                        Color.parseColor(noteSwm.getNote().getColor())
                );
            }
        }
    }
}
