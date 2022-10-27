package com.groupx.simplenote.entity;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(primaryKeys = {"accountId", "noteId"})
public class NoteAccount implements Serializable {
    private int accountId;
    private int noteId;
    private String permission;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
