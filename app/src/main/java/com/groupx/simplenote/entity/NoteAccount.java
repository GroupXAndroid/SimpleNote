package com.groupx.simplenote.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NoteAccount implements Serializable {
    @PrimaryKey
    private int accountId;
    @PrimaryKey
    private int noteId;
    private int permission;
}
