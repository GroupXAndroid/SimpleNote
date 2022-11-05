package com.groupx.simplenote.entity;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(primaryKeys = {"noteId", "tagId"})
public class NoteTag implements Serializable {
    private int noteId;
    private int tagId;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
