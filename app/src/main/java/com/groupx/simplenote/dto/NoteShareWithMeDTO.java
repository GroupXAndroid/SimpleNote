package com.groupx.simplenote.dto;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;

public class NoteShareWithMeDTO {
    @Embedded
    private Note note;
    @Embedded
    private Account account;
    @ColumnInfo(name = "permission")
    private String permissions;

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
