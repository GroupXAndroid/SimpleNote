package com.groupx.simplenote.listener;

import com.groupx.simplenote.entity.Note;

public interface NoteListener {
    void onNoteClicked(Note note, int position);
}
